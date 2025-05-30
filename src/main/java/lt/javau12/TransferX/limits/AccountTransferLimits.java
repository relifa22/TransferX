package lt.javau12.TransferX.limits;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountTransferLimits {

    private final BigDecimal maxDailyLimit = new BigDecimal("500.00");
    private final BigDecimal maxMonthlyLimit = new BigDecimal("5000.00");

    public BigDecimal getMaxDailyLimit() {
        return maxDailyLimit;
    }

    public BigDecimal getMaxMonthlyLimit() {
        return maxMonthlyLimit;
    }
}
