package lt.javau12.TransferX.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public class AccountLimitDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long accountId;

    @DecimalMin(value = "0.0", inclusive = false, message = "Daily transfer limit must be greater than 0")
    @DecimalMax(value = "500.00", message = "Daily transfer limit cannot exceed 500 €")
    private BigDecimal transferDailyLimit;

    @DecimalMin(value = "0.0", inclusive = false, message = "Monthly transfer limit must be greater than 0")
    @DecimalMax(value = "5000.00", message = "Monthly transfer limit cannot exceed 5000 €")
    private BigDecimal transferMonthlyLimit;

    public AccountLimitDto(){


    }

    public AccountLimitDto(Long accountIdd,
                           BigDecimal transferDailyLimit,
                           BigDecimal transferMonthlyLimit) {

        this.accountId = accountIdd;

        this.transferDailyLimit = transferDailyLimit;
        this.transferMonthlyLimit = transferMonthlyLimit;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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
