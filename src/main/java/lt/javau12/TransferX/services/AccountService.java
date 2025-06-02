package lt.javau12.TransferX.services;

import lt.javau12.TransferX.DTO.AccountLimitDto;
import lt.javau12.TransferX.DTO.AccountResponseDto;
import lt.javau12.TransferX.entities.Account;
import lt.javau12.TransferX.entities.Client;
import lt.javau12.TransferX.enums.AccountType;
import lt.javau12.TransferX.enums.CurrencyType;
import lt.javau12.TransferX.exeptions.NotFoundExeption;
import lt.javau12.TransferX.exeptions.ValidationException;
import lt.javau12.TransferX.formatters.IbanGenerator;
import lt.javau12.TransferX.limits.AccountTransferLimits;
import lt.javau12.TransferX.mappers.AccountMapper;
import lt.javau12.TransferX.repositories.AccountRepository;
import lt.javau12.TransferX.repositories.ClientRepository;
import lt.javau12.TransferX.validators.ClientValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final ClientRepository clientRepository;
    private final IbanGenerator ibanGenerator;
    private final ClientValidator clientValidator;
    private final CardService cardService;
    private final AccountTransferLimits accountTransferLimits;


    public AccountService(AccountRepository accountRepository,
                          AccountMapper accountMapper,
                          ClientRepository clientRepository,
                          IbanGenerator ibanGenerator,
                          ClientValidator clientValidator,
                          CardService cardService,
                          AccountTransferLimits accountTransferLimits) {

        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.clientRepository = clientRepository;
        this.ibanGenerator = ibanGenerator;
        this.clientValidator = clientValidator;
        this.cardService = cardService;
        this.accountTransferLimits = accountTransferLimits;
    }

    // automatinis saskaitos kurimas kuriant vartotoja
    public AccountResponseDto createDefaultAccountForClient(Client client) {


        Account account = new Account();
        account.setClient(client);
        account.setCurrencyType(CurrencyType.EUR);
        account.setAccountType(AccountType.ADULT);
        account.setIban(ibanGenerator.generateUniqueIban());
        //nustatomas pradinis 100 eur balansas, kad galima butu testuoti pavedimus ir atsiskaitymus
        account.setBalance(BigDecimal.valueOf(100));

        account.setDailyTransferLimit(accountTransferLimits.getMaxDailyLimit());
        account.setMonthlyTransferLimit(accountTransferLimits.getMaxMonthlyLimit());

        Account saved = accountRepository.save(account);

        return accountMapper.toDto(saved);

    }

    // default account pagal clientId,
    public AccountResponseDto getDefaultAccountByClientId(Long clientId) {
        return accountRepository.findByClientId(clientId).stream()
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
    public List<AccountResponseDto> getAccountsByClientId(Long clientId) {
        return accountRepository.findByClientId(clientId).stream()
                .map(accountMapper::toDto)
                .toList();
    }

    // papildomos saskaitos kurimas nurodant saskaitos tipa
    public AccountResponseDto createAccountForClient(Long clientId) {

        return clientRepository.findById(clientId)
                .filter(client -> client.getClientType() != null)
                .map(client -> {
                    clientValidator.validateSelectedTypeAgainstBirthDate(client.getClientType(), client.getBirthDate());

                    AccountType accountType = switch (client.getClientType()) {
                        case CHILD -> AccountType.CHILD;
                        case TEENAGER -> AccountType.TEENAGER;
                        case ADULT -> AccountType.ADULT;
                    };

                    Account account = new Account();
                    account.setClient(client);
                    account.setCurrencyType(CurrencyType.EUR);
                    account.setAccountType(accountType);
                    account.setIban(ibanGenerator.generateUniqueIban());

                    return accountMapper.toDto(accountRepository.save(account));
                })
                .orElseThrow(() -> new ValidationException("User not found or missing user type"));

    }

    public AccountLimitDto updateAccountLimits(Long accountId, AccountLimitDto accountLimitDto){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundExeption("Account not found"));

        if (accountLimitDto.getTransferDailyLimit().compareTo(accountTransferLimits.getMaxDailyLimit()) > 0 ||
        accountLimitDto.getTransferMonthlyLimit().compareTo(accountTransferLimits.getMaxMonthlyLimit()) > 0){
            throw new ValidationException("Limits exceed allowed maximum.");
        }

        account.setDailyTransferLimit(accountLimitDto.getTransferDailyLimit());
        account.setMonthlyTransferLimit(accountLimitDto.getTransferMonthlyLimit());

        Account saved = accountRepository.save(account);

        return new AccountLimitDto(
                saved.getDailyTransferLimit(),
                saved.getMonthlyTransferLimit()
        );
    }
}