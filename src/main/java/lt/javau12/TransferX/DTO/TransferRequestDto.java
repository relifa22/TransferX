package lt.javau12.TransferX.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransferRequestDto {

    @NotNull(message = "Sender account ID is required")
    private Long fromAccountId;

    @NotBlank(message = "Receiver full name is required")
    private String receiverFullName;

    @NotBlank(message = "Receiver bank is required")
    private String receiverBank;

    @NotBlank(message = "Recipient IBAN is required")
    private String toIban;

    @NotNull(message = "Amount must be specified")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    private String description;

    public TransferRequestDto(){

    }

    public TransferRequestDto(Long fromAccountId,
                              String receiverFullName,
                              String receiverBank,
                              String toIban,
                              BigDecimal amount,
                              String description) {

        this.fromAccountId = fromAccountId;
        this.receiverFullName = receiverFullName;
        this.receiverBank = receiverBank;
        this.toIban = toIban;
        this.amount = amount;
        this.description = description;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public String getReceiverFullName() {
        return receiverFullName;
    }

    public void setReceiverFullName(String receiverFullName) {
        this.receiverFullName = receiverFullName;
    }

    public String getReceiverBank() {
        return receiverBank;
    }

    public void setReceiverBank(String receiverBank) {
        this.receiverBank = receiverBank;
    }

    public String getToIban() {
        return toIban;
    }

    public void setToIban(String toIban) {
        this.toIban = toIban;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
