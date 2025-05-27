package lt.javau12.TransferX.mappers;

import lt.javau12.TransferX.DTO.AccountListDto;
import lt.javau12.TransferX.DTO.AccountResponseDto;
import lt.javau12.TransferX.DTO.CreateAccountDto;
import lt.javau12.TransferX.entities.Account;
import lt.javau12.TransferX.formatters.IbanGenerator;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    private final IbanGenerator ibanGenerator;

    public AccountMapper(IbanGenerator ibanGenerator) {
        this.ibanGenerator = ibanGenerator;
    }

    public Account toEntity(CreateAccountDto createAccountDto){
      Account account = new Account();

      account.setAccountType(createAccountDto.getAccountType());
      account.setCurrencyType(createAccountDto.getCurrencyType());
      account.setIban(ibanGenerator.generateIban());

      return account;
    }

    public AccountResponseDto toDto(Account account){
        return new AccountResponseDto(
                account.getId(),
                account.getIban(),
                account.getBalance(),
                account.getCurrencyType(),
                account.getAccountType()
        );
    }

    public AccountListDto toListDto(Account account){

        AccountListDto accountListDto = new AccountListDto();

        accountListDto.setAccountId(account.getId());
        accountListDto.setIban(account.getIban());
        accountListDto.setCurrencyType(account.getCurrencyType());
        accountListDto.setAccountType(account.getAccountType());
        accountListDto.setUserId(account.getUser().getId());
        return accountListDto;
    }



}
