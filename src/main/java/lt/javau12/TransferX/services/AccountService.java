package lt.javau12.TransferX.services;

import lt.javau12.TransferX.DTO.AccountResponseDto;
import lt.javau12.TransferX.entities.Account;
import lt.javau12.TransferX.entities.User;
import lt.javau12.TransferX.enums.AccountType;
import lt.javau12.TransferX.enums.CurrencyType;
import lt.javau12.TransferX.exeptions.NotFoundExeption;
import lt.javau12.TransferX.exeptions.ValidationException;
import lt.javau12.TransferX.formatters.IbanGenerator;
import lt.javau12.TransferX.mappers.AccountMapper;
import lt.javau12.TransferX.repositories.AccountRepository;
import lt.javau12.TransferX.repositories.UserRepository;
import lt.javau12.TransferX.validators.UserValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserRepository userRepository;
    private final IbanGenerator ibanGenerator;
    private final UserValidator userValidator;
    private final CardService cardService;


    public AccountService(AccountRepository accountRepository,
                          AccountMapper accountMapper,
                          UserRepository userRepository,
                          IbanGenerator ibanGenerator,
                          UserValidator userValidator,
                          CardService cardService) {

        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.userRepository = userRepository;
        this.ibanGenerator = ibanGenerator;
        this.userValidator = userValidator;
        this.cardService = cardService;
    }

    // automatinis saskaitos kurimas kuriant vartotoja
    public AccountResponseDto createDefaultAccountForUser(User user) {


        Account account = new Account();
        account.setUser(user);
        account.setCurrencyType(CurrencyType.EUR);
        account.setAccountType(AccountType.ADULT);
        account.setIban(ibanGenerator.generateUniqueIban());
        //nustatomas pradinis 100 eur balansas, kad galima butu testuoti pavedimus ir atsiskaitymus
        account.setBalance(BigDecimal.valueOf(100));

        Account saved = accountRepository.save(account);

        return accountMapper.toDto(saved);

    }

    // default account pagal userId,
    public AccountResponseDto getDefaultAccountByUserId(Long userId) {
        return accountRepository.findByUserId(userId).stream()
                .filter(account -> account.getCurrencyType() == CurrencyType.EUR
                        && account.getAccountType() == AccountType.ADULT)
                .findFirst()
                .map(accountMapper::toDto)
                .orElseThrow(() -> new NotFoundExeption("Default account not found"));
    }

    // visu saskaitu gavimas
    public List<AccountResponseDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(accountMapper::toDto)
                .toList();
    }

    //pagal saskaitos id
    public Optional<AccountResponseDto> getAccountById(Long id) {
        return accountRepository.findById(id)
                .map(accountMapper::toDto);
    }

    // vartotojo saskaitos pagal id
    public List<AccountResponseDto> getAccountsByUserId(Long userId) {
        return accountRepository.findByUserId(userId).stream()
                .map(accountMapper::toDto)
                .toList();
    }

    // papildomos saskaitos kurimas nurodant saskaitos tipa
    public AccountResponseDto createAccountForUser(Long userId) {

        return userRepository.findById(userId)
                .filter(user -> user.getUserType() != null)
                .map(user -> {
                    userValidator.validateSelectedTypeAgainstBirthDate(user.getUserType(), user.getBirthDate());

                    AccountType accountType = switch (user.getUserType()) {
                        case CHILD -> AccountType.CHILD;
                        case TEENAGER -> AccountType.TEENAGER;
                        case ADULT -> AccountType.ADULT;
                    };

                    Account account = new Account();
                    account.setUser(user);
                    account.setCurrencyType(CurrencyType.EUR);
                    account.setAccountType(accountType);
                    account.setIban(ibanGenerator.generateUniqueIban());

                    return accountMapper.toDto(accountRepository.save(account));
                })
                .orElseThrow(() -> new ValidationException("User not found or missing user type"));

    }
}