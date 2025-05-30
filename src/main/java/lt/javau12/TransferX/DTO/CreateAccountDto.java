package lt.javau12.TransferX.DTO;

import jakarta.validation.constraints.NotNull;
import lt.javau12.TransferX.enums.AccountType;
import lt.javau12.TransferX.enums.CurrencyType;



public class CreateAccountDto {

    @NotNull(message = "Currency is required")
    private CurrencyType currencyType;

    @NotNull(message = "Account type is required")
    private AccountType accountType;


    public CreateAccountDto(){

    }

    public CreateAccountDto(CurrencyType currencyType,
                            AccountType accountType) {

        this.currencyType = currencyType;
        this.accountType = accountType;

    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }


}
