package lt.javau12.TransferX.services;

import jakarta.transaction.Transactional;
import lt.javau12.TransferX.DTO.*;
import lt.javau12.TransferX.entities.Account;
import lt.javau12.TransferX.entities.Card;
import lt.javau12.TransferX.entities.User;
import lt.javau12.TransferX.enums.UserType;
import lt.javau12.TransferX.exeptions.DuplicateEmailException;
import lt.javau12.TransferX.exeptions.ValidationException;
import lt.javau12.TransferX.mappers.ChildMapper;
import lt.javau12.TransferX.mappers.UserMapper;
import lt.javau12.TransferX.repositories.AccountRepository;
import lt.javau12.TransferX.repositories.CardRepository;
import lt.javau12.TransferX.repositories.UserRepository;
import lt.javau12.TransferX.validators.UserValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AccountService accountService;
    private final UserValidator userValidator;
    private final ChildMapper childMapper;
    private final AccountRepository accountRepository;
    private final CardService cardService;
    private final CardRepository cardRepository;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       AccountService accountService,
                       UserValidator userValidator,
                       ChildMapper childMapper,
                       AccountRepository accountRepository,
                       CardService cardService, CardRepository cardRepository) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.accountService = accountService;
        this.userValidator = userValidator;
        this.childMapper = childMapper;
        this.accountRepository = accountRepository;
        this.cardService = cardService;
        this.cardRepository = cardRepository;
    }

    // sukuriamas naujas vartotojas
    public UserDto createUser(CreateUserDto createUserDto){

        if (userRepository.existsByEmail(createUserDto.getEmail())){
            throw new DuplicateEmailException("Email already exists");
        }

        User user = userMapper.toEntity(createUserDto);
        user.setPassword(createUserDto.getPassword());

        // tikrinam ar gimimo data ir ak sutampa
         userValidator.doesPersonalCodeMatchBirthday(
                 user.getPersonalIdentificationNumber(),
                user.getBirthDate());

        //nustatomas vartotojo tipas pagal metus
        user.setUserType(userValidator.determineUserType(user.getBirthDate()));

        if (user.getUserType() != UserType.ADULT){
            throw new ValidationException("Only adult users can register");
        }

        User savedUser = userRepository.save(user);

        //automatinis saskaitos sukurimas pilnameciui
        if (savedUser.getUserType() == UserType.ADULT){
            accountService.createDefaultAccountForUser(savedUser);
        }
        return userMapper.toDto(savedUser);
    }

    //sukuriamas child vartotojas
    @Transactional // Užtikrina, kad jei įvyksta klaida bet kurioje šio metodo vietoje,
                   // visi veiksmai (vartotojo, sąskaitos, kortelės kūrimas) bus atšaukti (rollback)
    public ChildResponseDto createChilduser(CreateChildDto createChildDto){

        // susirandam teva
        User parent = userRepository.findById(createChildDto.getParentId())
                .orElseThrow(() -> new ValidationException("Parent not found"));

        User child = childMapper.toEntity(createChildDto);

        userValidator.doesPersonalCodeMatchBirthday(child.getPersonalIdentificationNumber(),
                child.getBirthDate()
        );

        UserType actualType = userValidator.determineUserType(child.getBirthDate());
        child.setUserType(actualType);
        child.setParent(parent);
        child.setCountry(parent.getCountry());
        child.setCity(parent.getCity());
        child.setAddress(parent.getAddress());

        if (userRepository.existsByPersonalIdentificationNumber(child.getPersonalIdentificationNumber()))
            throw new ValidationException("User with this personal ID exists.");

        User savedChild = userRepository.save(child);
        AccountResponseDto accountResponseDto = accountService.createAccountForUser(savedChild.getId());

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
    public List<UserDto> getAllUsers(){
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }
    // vartotojas pagal id
    public Optional<UserDto> getUserById(Long id){
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    // vaikai pagal tevo id
    public List<ChildListDto> getChildrenByParentId(Long parentId) {
        List<User> children = userRepository.findAllByParentId(parentId);

        return children.stream().map(child -> {
            List<Account> accounts = accountRepository.findByUserId(child.getId());

            List<AccountWithCardsDto> accountDtos = accounts.stream().map(account -> {
                List<Card> cards = cardRepository.findAllByAccountId(account.getId());

                List<CardResponseDto> cardDtos = cards.stream().map(card ->
                        new CardResponseDto(
                                card.getId(),
                                card.getCardType(),
                                card.getCardBrand(),
                                card.getCardNumber(),
                                card.getExpirationDate(),
                                card.isActive()
                        )
                ).toList();

                return new AccountWithCardsDto(account.getIban(), cardDtos);
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

    // istrinam vaika su kortelem ir saskaita,
    public boolean deleteChildById(Long childId){
        return userRepository.findById(childId)
                .flatMap(child -> accountRepository.findFirstByUserId(childId)
                        .flatMap(account -> cardRepository.findByAccountId(account.getId())
                                .map(card -> {
                                    cardRepository.delete(card);
                                    accountRepository.delete(account);
                                    userRepository.delete(child);
                                    return true;
                                })
                        )
                )
                .orElse(false);
    }






}
