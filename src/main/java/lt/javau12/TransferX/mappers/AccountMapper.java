package lt.javau12.TransferX.mappers;

import lt.javau12.TransferX.DTO.AccountResponseDto;
import lt.javau12.TransferX.DTO.CreateAccountDto;
import lt.javau12.TransferX.entities.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account toEntity(CreateAccountDto createAccountDto){
        return new Account(
                createAccountDto.getAccountType(),
                createAccountDto.getCurrencyType()
        );
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
