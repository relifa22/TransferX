package lt.javau12.TransferX.services;

import jakarta.transaction.Transactional;
import lt.javau12.TransferX.DTO.*;
import lt.javau12.TransferX.entities.Account;
import lt.javau12.TransferX.entities.Card;
import lt.javau12.TransferX.entities.Client;
import lt.javau12.TransferX.enums.ClientType;
import lt.javau12.TransferX.exeptions.DuplicateEmailException;
import lt.javau12.TransferX.exeptions.NotFoundException;
import lt.javau12.TransferX.exeptions.ValidationException;
import lt.javau12.TransferX.mappers.CardMapper;
import lt.javau12.TransferX.mappers.ChildMapper;
import lt.javau12.TransferX.mappers.ClientMapper;
import lt.javau12.TransferX.repositories.AccountRepository;
import lt.javau12.TransferX.repositories.CardRepository;
import lt.javau12.TransferX.repositories.ClientRepository;
import lt.javau12.TransferX.validators.ClientValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final AccountService accountService;
    private final ClientValidator clientValidator;
    private final ChildMapper childMapper;
    private final AccountRepository accountRepository;
    private final CardService cardService;
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final PasswordEncoder encoder;


    public ClientService(ClientRepository clientRepository,
                         ClientMapper clientMapper,
                         AccountService accountService,
                         ClientValidator clientValidator,
                         ChildMapper childMapper,
                         AccountRepository accountRepository,
                         CardService cardService,
                         CardRepository cardRepository,
                         CardMapper cardMapper,
                         PasswordEncoder encoder) {

        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.accountService = accountService;
        this.clientValidator = clientValidator;
        this.childMapper = childMapper;
        this.accountRepository = accountRepository;
        this.cardService = cardService;
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
        this.encoder = encoder;
    }

    // sukuriamas naujas vartotojas
    public ClientDto createClient(CreateClientDto createClientDto){

        if (clientRepository.existsByEmail(createClientDto.getEmail())){
            throw new DuplicateEmailException("Email already exists");
        }

        Client client = clientMapper.toEntity(createClientDto);
        client.setPassword(encoder.encode(createClientDto.getPassword()));

        // tikrinam ar gimimo data ir ak sutampa
         clientValidator.doesPersonalCodeMatchBirthday(
                 client.getPersonalIdentificationNumber(),
                 client.getBirthDate());

        //nustatomas vartotojo tipas pagal metus
        client.setClientType(clientValidator.determineClientType(client.getBirthDate()));

        if (client.getClientType() != ClientType.ADULT){
            throw new ValidationException("Only adult users can register");
        }

        Client savedClient = clientRepository.save(client);

        //automatinis saskaitos sukurimas pilnameciui
        if (savedClient.getClientType() == ClientType.ADULT){
            accountService.createDefaultAccountForClient(savedClient);
        }
        return clientMapper.toDto(savedClient);
    }

    //sukuriamas child vartotojas
    //@Transactional Užtikrina, kad jei įvyksta klaida bet kurioje šio metodo vietoje,
    // visi veiksmai (vartotojo, sąskaitos, kortelės kūrimas) bus atšaukti (rollback)
    @Transactional
    public ChildResponseDto createChildClient(CreateChildDto createChildDto){

        // susirandam teva
        Client parent = clientRepository.findById(createChildDto.getParentId())
                .orElseThrow(() -> new ValidationException("Parent not found"));

        Client child = childMapper.toEntity(createChildDto);

        clientValidator.doesPersonalCodeMatchBirthday(child.getPersonalIdentificationNumber(),
                child.getBirthDate()
        );

        ClientType actualType = clientValidator.determineClientType(child.getBirthDate());

        child.setClientType(actualType);
        child.setParent(parent);
        child.setCountry(parent.getCountry());
        child.setCity(parent.getCity());
        child.setAddress(parent.getAddress());

        if (clientRepository.existsByPersonalIdentificationNumber(child.getPersonalIdentificationNumber()))
            throw new ValidationException("User with this personal ID exists.");

        Client savedChild = clientRepository.save(child);
        AccountResponseDto accountResponseDto = accountService.createAccountForClient(savedChild.getId());

        Account account = accountRepository.findById(accountResponseDto.getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));
        cardService.createDefaultCardForChildAccount(account.getId());

        String message = switch (actualType) {
            case CHILD -> "Created child account with Mastercard type card. Account and card limits are for child.";
            case TEENAGER -> "Created teenager account with Mastercard type card. Account and card limits are for teenager.";
            default -> throw new ValidationException("Unrecognized user type: " + actualType);
        };


        return childMapper.toChildDto(savedChild, message);

    }

    //visi vartotojai
    public List<ClientDto> getAllClients(){
        return clientRepository.findAll().stream()
                .map(clientMapper::toDto)
                .toList();
    }

    // vartotojas pagal id
    public Optional<ClientDto> getClientById(Long id){
        return clientRepository.findById(id)
                .map(clientMapper::toDto);
    }

    // vaikai pagal tevo id
    public List<ChildListDto> getChildrenByParentId(Long parentId) {
        List<Client> children = clientRepository.findAllByParentId(parentId);

        return children.stream().map(child -> {
            List<Account> accounts = accountRepository.findByClientId(child.getId());

            List<AccountWithCardsDto> accountDtos = accounts.stream().map(account -> {
                List<Card> cards = cardRepository.findAllByAccountId(account.getId());

                List<CardResponseDto> cardDtos = cards.stream()
                        .map(cardMapper::toDto)
                        .toList();

                return new AccountWithCardsDto(account.getId(),account.getIban(), account.getBalance(), cardDtos);
            }).toList();

            return new ChildListDto(
                    child.getId(),
                    child.getName(),
                    child.getLastName(),
                    child.getPersonalIdentificationNumber(),
                    accountDtos
            );
        }).toList();
    }

    //adreso updat'as
    public ClientDto updateAddress(UpdateAddressDto updateAddressDto){
        Client client = clientRepository.findById(updateAddressDto.getClientId())
                .orElseThrow(() -> new NotFoundException("User not found "));

        client.setCountry(updateAddressDto.getCountry());
        client.setCity(updateAddressDto.getCity());
        client.setAddress(updateAddressDto.getAddress());

        clientRepository.save(client);
        return clientMapper.toDto(client);



    }

    // istrinam vaika su kortelem ir saskaita,
    public boolean deleteChildById(Long childId){
        return clientRepository.findById(childId)
                .flatMap(child -> accountRepository.findFirstByClientId(childId)
                        .flatMap(account -> {
                            if (account.getBalance().compareTo(BigDecimal.ZERO) > 0){
                                throw new ValidationException("Cannot delete account with remaining balance. Please clear balance first.");
                            }
                            return cardRepository.findByAccountId(account.getId())
                                    .map(card -> {
                                        cardRepository.delete(card);
                                        accountRepository.delete(account);
                                        clientRepository.delete(child);
                                        return true;
                                    });
                                }
                        )
                )
                .orElseThrow(()-> new NotFoundException("User not found by id: " + childId));
    }


    // istrinam suaugusi
    public boolean deleteAdultById(Long id){
        return clientRepository.findById(id)
                .map(client -> {
                    if (client.getClientType() != ClientType.ADULT) {
                        throw new ValidationException("Only adult users can be deleted via this endpoint.");
                    }

                    List<Client> children = clientRepository.findAllByParentId(client.getId());
                    if (!children.isEmpty()) {
                        throw new ValidationException("Cannot delete client: child account(s) still exist. Please remove them first.");
                    }

                    List<Account> accounts = accountRepository.findByClientId(client.getId());
                    boolean hasBalance = accounts.stream()
                                    .anyMatch(account -> account.getBalance().compareTo(BigDecimal.ZERO) > 0);
                    if (hasBalance){
                        throw new ValidationException("Cannot delete account with remaining balance. Please clear your balance first.");
                    }

                    boolean hasAnyCards = accounts.stream()
                            .anyMatch(account -> !account.getCards().isEmpty());

                    if (hasAnyCards) {
                        throw new ValidationException("Cannot delete client: account(s) still have cards. Please remove all cards first.");
                    }


                    accountRepository.deleteAll(accounts);
                    clientRepository.delete(client);
                    return true;
                })

                .orElseThrow(()-> new NotFoundException("Client not found by id: " + id));
    }

    //pilnam info apie klientą su saskiatom, vaikais kortelem
    public ClientFullInfoDto getFullInfoByClientId(Long clientId){
        Client client = clientRepository.findById(clientId)
                .orElseThrow(()-> new NotFoundException("User not found by clientId: " + clientId));

        List<AccountWithCardsDto> accountWithCardsDto = buildAccountWithCards(client.getId());
        List<ChildListDto> childrenDto = buildChildrenInfo(client);

        return new ClientFullInfoDto(
                client.getId(),
                client.getName(),
                client.getLastName(),
                client.getEmail(),
                accountWithCardsDto,
                childrenDto

        );
    }

    //randamos visos saskaitos ir jom priklausancios korteles pagal clientId
    private List<AccountWithCardsDto> buildAccountWithCards(Long clientId) {
        List<Account> accounts = accountRepository.findByClientId(clientId);

        return accounts.stream()
                .map(account -> {
                    Optional<Card> optionalCard = cardRepository.findByAccountId(account.getId());

                    List<CardResponseDto> cardDto = optionalCard
                            .map(card -> List.of(cardMapper.toDto(card)))
                            .orElse(List.of());

                    return new AccountWithCardsDto(account.getId(),
                            account.getIban(),
                            account.getBalance(),
                            cardDto);
                })
                .toList();
    }

    // randami vaikai pagal parentID
    private List<ChildListDto> buildChildrenInfo(Client parent) {
        if (parent.getClientType() != ClientType.ADULT) {
            return List.of();
        }

        List<Client> children = clientRepository.findAllByParentId(parent.getId());

        return children.stream()
                .map(child -> new ChildListDto(
                        child.getId(),
                        child.getName(),
                        child.getLastName(),
                        child.getPersonalIdentificationNumber(),
                        buildAccountWithCards(child.getId())
                ))
                .toList();
    }

    // visi klientai su pilna info
    public List<ClientFullInfoDto> getAllClientsFullInfo() {
        return clientRepository.findAll().stream()
                .filter(client -> client.getClientType() == ClientType.ADULT)
                .map(client -> new ClientFullInfoDto(
                        client.getId(),
                        client.getName(),
                        client.getLastName(),
                        client.getEmail(),
                        buildAccountWithCards(client.getId()),
                        buildChildrenInfo(client)
                ))
                .toList();
    }





}
