package lt.javau12.TransferX.DTO;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public class AccountLimitDto {


    @DecimalMin(value = "0.0", inclusive = false, message = "Daily transfer limit must be greater than 0")
    @DecimalMax(value = "500.00", message = "Daily transfer limit cannot exceed 500 €")
    private BigDecimal transferDailyLimit;

    @DecimalMin(value = "0.0", inclusive = false, message = "Monthly transfer limit must be greater than 0")
    @DecimalMax(value = "5000.00", message = "Monthly transfer limit cannot exceed 5000 €")
    private BigDecimal transferMonthlyLimit;

    public AccountLimitDto(){

    }

    public AccountLimitDto(BigDecimal transferDailyLimit,
                           BigDecimal transferMonthlyLimit) {

        this.transferDailyLimit = transferDailyLimit;
        this.transferMonthlyLimit = transferMonthlyLimit;
    }

    public BigDecimal getTransferDailyLimit() {
        return transferDailyLimit;
    }

    public void setTransferDailyLimit(BigDecimal transferDailyLimit) {
        this.transferDailyLimit = transferDailyLimit;
    }

    public BigDecimal getTransferMonthlyLimit() {
        return transferMonthlyLimit;
    }

    public void setTransferMonthlyLimit(BigDecimal transferMonthlyLimit) {
        this.transferMonthlyLimit = transferMonthlyLimit;
    }
}
