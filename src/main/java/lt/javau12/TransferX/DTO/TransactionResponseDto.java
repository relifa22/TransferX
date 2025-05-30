package lt.javau12.TransferX.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lt.javau12.TransferX.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponseDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private TransactionType transactionType;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer number;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime timestamp;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String senderFullName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal amount;

    public TransactionResponseDto(){

    }

    public TransactionResponseDto(TransactionType transactionType,
                                  Integer number,
                                  LocalDateTime timesamp,
                                  String fullname,
                                  String descriprion,
                                  BigDecimal amount) {

        this.transactionType = transactionType;
        this.number = number;
        this.timestamp = timesamp;
        this.senderFullName = fullname;
        this.description = descriprion;
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
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

    public String getSenderFullName() {
        return senderFullName;
    }

    public void setSenderFullName(String senderFullName) {
        this.senderFullName = senderFullName;
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
