package lt.javau12.TransferX.DTO;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CardLimitDto {

    @NotNull(message = "Card ID is required")
    private Long cardId;

    @DecimalMin(value = "0.01", message = "Daily limit must be greater than 0")
    @DecimalMax(value = "500.00", message = "Daily limit cannot exceed 500 €")
    private BigDecimal paymentDailyLimit;

    @DecimalMin(value = "0.01", message = "Weekly limit must be greater than 0")
    @DecimalMax(value = "2000.00", message = "Weekly limit cannot exceed 2000 €")
    private BigDecimal paymentWeeklyLimit;

    @DecimalMin(value = "0.01", message = "Monthly limit must be greater than 0")
    @DecimalMax(value = "2000.00", message = "Monthly limit cannot exceed 2000 €")
    private BigDecimal paymentMonthlyLimit;

    public CardLimitDto(){

    }

    public CardLimitDto(Long cardId,
                        BigDecimal paymentDailyLimit,
                        BigDecimal paymentWeeklyLimit,
                        BigDecimal paymentMonthlyLimit) {

        this.cardId = cardId;
        this.paymentDailyLimit = paymentDailyLimit;
        this.paymentWeeklyLimit = paymentWeeklyLimit;
        this.paymentMonthlyLimit = paymentMonthlyLimit;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public BigDecimal getPaymentDailyLimit() {
        return paymentDailyLimit;
    }

    public void setPaymentDailyLimit(BigDecimal paymentDailyLimit) {
        this.paymentDailyLimit = paymentDailyLimit;
    }

    public BigDecimal getPaymentWeeklyLimit() {
        return paymentWeeklyLimit;
    }

    public void setPaymentWeeklyLimit(BigDecimal paymentWeeklyLimit) {
        this.paymentWeeklyLimit = paymentWeeklyLimit;
    }

    public BigDecimal getPaymentMonthlyLimit() {
        return paymentMonthlyLimit;
    }

    public void setPaymentMonthlyLimit(BigDecimal paymentMonthlyLimit) {
        this.paymentMonthlyLimit = paymentMonthlyLimit;
    }
}
