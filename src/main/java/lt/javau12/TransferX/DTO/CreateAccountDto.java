package lt.javau12.TransferX.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lt.javau12.TransferX.enums.AccountType;

import java.math.BigDecimal;

public class CreateAccountDto {

    @NotBlank(message = "Valiuta privaloma")
    private String curency;

    @NotNull(message = "Sąskaitos tipas privalomas")
    private AccountType accountType;

    @DecimalMin(value = "0.0", inclusive = false, message = "dienos limitas turi buti didesnis  už 0")
    private BigDecimal daylyLimit;

    @DecimalMin(value = "0.0", inclusive = false, message = "dienos limitas turi buti didesnis  už 0")
    private BigDecimal monthlyLimit;

    public CreateAccountDto(){

    }

    public CreateAccountDto(String curency,
                            AccountType accountType,
                            BigDecimal daylyLimit,
                            BigDecimal monthlyLimit) {

        this.curency = curency;
        this.accountType = accountType;
        this.daylyLimit = daylyLimit;
        this.monthlyLimit = monthlyLimit;
    }


}
