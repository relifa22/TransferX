package lt.javau12.TransferX.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponseDto {

    private Integer number;
    private LocalDateTime timestamp;
    private String fullName;
    private String description;
    private BigDecimal amount;

    public TransactionResponseDto(){

    }

    public TransactionResponseDto(Integer number,
                                  LocalDateTime timesamp,
                                  String fullname,
                                  String descriprion,
                                  BigDecimal amount) {
        this.number = number;
        this.timestamp = timesamp;
        this.fullName = fullname;
        this.description = descriprion;
        this.amount = amount;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
