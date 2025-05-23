package lt.javau12.TransferX.mappers;

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



}
