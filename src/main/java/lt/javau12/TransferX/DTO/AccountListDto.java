package lt.javau12.TransferX.DTO;

import lt.javau12.TransferX.enums.AccountType;
import lt.javau12.TransferX.enums.CurrencyType;

public class AccountListDto {
    private Long accountId;
    private String iban;
    private CurrencyType currencyType;
    private AccountType accountType;
    private Long clientId;

    public AccountListDto(){

    }

    public AccountListDto(Long accountId,
                          String iban,
                          CurrencyType currencyType,
                          AccountType accountType,
                          Long clientId) {

        this.accountId = accountId;
        this.iban = iban;
        this.currencyType = currencyType;
        this.accountType = accountType;
        this.clientId = clientId;
    }


    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
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

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
