package lt.javau12.TransferX.enums;

import java.math.BigDecimal;

public enum CardType {
    CHILD(
            new BigDecimal("10.00"),
            new BigDecimal("50.00"),
            new BigDecimal("200.00")

    ),
    TEENAGER(
            new BigDecimal("30.00"),
            new BigDecimal("200.00"),
            new BigDecimal("600.00")
    ),
    ADULT(
            new BigDecimal("500.00"),
            new BigDecimal("2000.00"),
            new BigDecimal("2000.00")
    );
    private final BigDecimal dailyLimit;
    private final BigDecimal weeklyLimit;
    private final BigDecimal monthlyLimit;

    CardType(BigDecimal dailyLimit, BigDecimal weeklyLimit, BigDecimal monthlyLimit) {
        this.dailyLimit = dailyLimit;
        this.weeklyLimit = weeklyLimit;
        this.monthlyLimit = monthlyLimit;
    }

    public BigDecimal getDailyLimit() {
        return dailyLimit;
    }

    public BigDecimal getWeeklyLimit() {
        return weeklyLimit;
    }

    public BigDecimal getMonthlyLimit() {
        return monthlyLimit;
    }
}
