package lt.javau12.TransferX.services;

import jakarta.transaction.Transactional;
import lt.javau12.TransferX.DTO.*;
import lt.javau12.TransferX.entities.Account;
import lt.javau12.TransferX.entities.Card;
import lt.javau12.TransferX.entities.User;
import lt.javau12.TransferX.enums.UserType;
import lt.javau12.TransferX.exeptions.DuplicateEmailException;
import lt.javau12.TransferX.exeptions.NotFoundExeption;
import lt.javau12.TransferX.exeptions.ValidationException;
import lt.javau12.TransferX.mappers.AccountMapper;
import lt.javau12.TransferX.mappers.CardMapper;
import lt.javau12.TransferX.mappers.ChildMapper;
import lt.javau12.TransferX.mappers.UserMapper;
import lt.javau12.TransferX.repositories.AccountRepository;
import lt.javau12.TransferX.repositories.CardRepository;
import lt.javau12.TransferX.repositories.UserRepository;
import lt.javau12.TransferX.validators.UserValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    private final CardMapper cardMapper;
    private final AccountMapper accountMapper;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       AccountService accountService,
                       UserValidator userValidator,
                       ChildMapper childMapper,
                       AccountRepository accountRepository,
                       CardService cardService,
                       CardRepository cardRepository,
                       CardMapper cardMapper, AccountMapper accountMapper) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.accountService = accountService;
        this.userValidator = userValidator;
        this.childMapper = childMapper;
        this.accountRepository = accountRepository;
        this.cardService = cardService;
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
        this.accountMapper = accountMapper;
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
    //@Transactional Užtikrina, kad jei įvyksta klaida bet kurioje šio metodo vietoje,
    // visi veiksmai (vartotojo, sąskaitos, kortelės kūrimas) bus atšaukti (rollback)
    @Transactional
    public ChildResponseDto createChildUser(CreateChildDto createChildDto){

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

    // istrinam vaika su kortelem ir saskaita,
    public boolean deleteChildById(Long childId){
        return userRepository.findById(childId)
                .flatMap(child -> accountRepository.findFirstByUserId(childId)
                        .flatMap(account -> {
                            if (account.getBalance().compareTo(BigDecimal.ZERO) > 0){
                                throw new ValidationException("Cannot delete acount with remaining balance. Please clear balance first.");
                            }
                            return cardRepository.findByAccountId(account.getId())
                                    .map(card -> {
                                        cardRepository.delete(card);
                                        accountRepository.delete(account);
                                        userRepository.delete(child);
                                        return true;
                                    });
                                }
                        )
                )
                .orElseThrow(()-> new NotFoundExeption("User not found by id: " + childId));
    }

    // istrinam suaugusi
    public boolean deleteAdultByid(Long id){
        return userRepository.findById(id)
                .map(user -> {
                    if (user.getUserType() != UserType.ADULT) {
                        throw new ValidationException("Only adult users can be deleted via this endpoint.");
                    }

                    List<User> children = userRepository.findAllByParentId(user.getId());
                    if (!children.isEmpty()) {
                        throw new ValidationException("Cannot delete user: child account(s) still exist. Please remove them first.");
                    }

                    List<Account> accounts = accountRepository.findByUserId(user.getId());
                    boolean hasBalance = accounts.stream()
                                    .anyMatch(account -> account.getBalance().compareTo(BigDecimal.ZERO) > 0);
                    if (hasBalance){
                        throw new ValidationException("Cannot delete account with remaining balance. Pleace clear your balance first.");
                    }

                    boolean hasAnyCards = accounts.stream()
                            .anyMatch(account -> !account.getCards().isEmpty());

                    if (hasAnyCards) {
                        throw new ValidationException("Cannot delete user: account(s) still have cards. Please remove all cards first.");
                    }


                    accountRepository.deleteAll(accounts);
                    userRepository.delete(user);
                    return true;
                })

                .orElseThrow(()-> new NotFoundExeption("User not found by id: " + id));
    }

    public UserFullInfoDto getFullInfoByUserId(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundExeption("User not found by userId: " +userId));
        List<AccountWithCardsDto> accountWithCardsDtos = buildAccountWithCards(user.getId());
        List<ChildListDto> childrenDto = buildChildrenInfo(user);

        return new UserFullInfoDto(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                accountWithCardsDtos,
                childrenDto

        );
    }

    private List<AccountWithCardsDto> buildAccountWithCards(Long userId) {
        List<Account> accounts = accountRepository.findByUserId(userId);

        return accounts.stream()
                .map(account -> {
                    Optional<Card> optionalCard = cardRepository.findByAccountId(account.getId());

                    List<CardResponseDto> cardDtos = optionalCard
                            .map(card -> List.of(cardMapper.toDto(card)))
                            .orElse(List.of());

                    return new AccountWithCardsDto(account.getId(), account.getIban(),account.getBalance(), cardDtos);
                })
                .toList();
    }

    private List<ChildListDto> buildChildrenInfo(User parent) {
        if (parent.getUserType() != UserType.ADULT) {
            return List.of();
        }

        List<User> children = userRepository.findAllByParentId(parent.getId());

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

    public List<UserFullInfoDto> getAllUsersFullInfo() {
        return userRepository.findAll().stream()
                .filter(user -> user.getUserType() == UserType.ADULT)
                .map(user -> new UserFullInfoDto(
                        user.getId(),
                        user.getName(),
                        user.getLastName(),
                        user.getEmail(),
                        buildAccountWithCards(user.getId()),
                        buildChildrenInfo(user)
                ))
                .toList();
    }





}
