package lt.javau12.TransferX.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lt.javau12.TransferX.enums.AccountType;
import lt.javau12.TransferX.enums.CurrencyType;

import java.math.BigDecimal;


public class AccountResponseDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String iban;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal balance;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CurrencyType currencyType;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private AccountType accountType;

    public AccountResponseDto(){

    }

    public AccountResponseDto(Long id,
                              String iban,
                              BigDecimal balance,
                              CurrencyType currencyType,
                              AccountType accountType) {
        this.id = id;
        this.iban = iban;
        this.balance = balance;
        this.currencyType = currencyType;
        this.accountType = accountType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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
