package lt.javau12.TransferX.DTO;

import java.math.BigDecimal;
import java.util.List;

public class AccountWithCardsDto {

    private Long accountId;
    private String iban;
    private BigDecimal balance;
    private List<CardResponseDto> cardResponseDtoList;



    public AccountWithCardsDto(Long accountId,
                               String iban,
                               BigDecimal balance,
                               List<CardResponseDto> cardResponseDtoList) {
        this.accountId = accountId;
        this.iban = iban;
        this.balance = balance;
        this.cardResponseDtoList = cardResponseDtoList;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<CardResponseDto> getCardResponseDtoList() {
        return cardResponseDtoList;
    }

    public void setCardResponseDtoList(List<CardResponseDto> cardResponseDtoList) {
        this.cardResponseDtoList = cardResponseDtoList;
    }
}
