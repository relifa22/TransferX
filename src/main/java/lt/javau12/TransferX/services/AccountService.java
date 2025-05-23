package lt.javau12.TransferX.services;

import lt.javau12.TransferX.DTO.AccountResponseDto;
import lt.javau12.TransferX.entities.Account;
import lt.javau12.TransferX.entities.User;
import lt.javau12.TransferX.enums.AccountType;
import lt.javau12.TransferX.enums.CurrencyType;
import lt.javau12.TransferX.formatters.IbanGenerator;
import lt.javau12.TransferX.mappers.AccountMapper;
import lt.javau12.TransferX.repositories.AccountRepository;
import lt.javau12.TransferX.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserRepository userRepository;
    private final IbanGenerator ibanGenerator;


    public AccountService(AccountRepository accountRepository,
                          AccountMapper accountMapper,
                          UserRepository userRepository,
                          IbanGenerator ibanGenerator) {

        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.userRepository = userRepository;
        this.ibanGenerator = ibanGenerator;
    }

    // automatinis saskaitos kurimas kuriant vartotoja
    public AccountResponseDto createDefaultAccountForUser(User user){


        Account account = new Account();
        account.setUser(user);
        account.setCurrencyType(CurrencyType.EUR);
        account.setAccountType(AccountType.ADULT);
        account.setIban(ibanGenerator.generateUniqueIban());

        Account saved = accountRepository.save(account);
        return accountMapper.toDto(saved);

    }

    // default account pagal userId,
    public AccountResponseDto getDefaultAccountByUserId(Long userId){
        return accountRepository.findByUserId(userId).stream()
                .filter(account -> account.getCurrencyType() == CurrencyType.EUR
                && account.getAccountType() == AccountType.ADULT)
                .findFirst()
                .map(accountMapper::toDto)
                .orElseThrow(()-> new RuntimeException("default account not found"));
    }
    // visu saskaitu gavimas
    public List<AccountResponseDto> getAllAccounts(){
        return accountRepository.findAll().stream()
                .map(accountMapper::toDto)
                .toList();
    }

    //pagal saskaitos id
    public Optional<AccountResponseDto> getAccountById(Long id){
        return accountRepository.findById(id)
                .map(accountMapper::toDto);
    }
    // visu saskaitu gavimas
    //pagal saskaitos id
    // vartotojo saskaitos pagal id
    public List<AccountResponseDto> getAccountsByUserId(Long userId){
        return  accountRepository.findByUserId(userId).stream()
                .map(accountMapper::toDto)
                .toList();
    }







}
