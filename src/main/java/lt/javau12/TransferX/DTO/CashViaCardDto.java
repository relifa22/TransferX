package lt.javau12.TransferX.DTO;

import java.math.BigDecimal;

public class CashViaCardDto {

    private Long cardId;
    private BigDecimal amount;

    public CashViaCardDto(Long cardId,
                          BigDecimal balance) {

        this.cardId = cardId;
        this.amount = balance;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
