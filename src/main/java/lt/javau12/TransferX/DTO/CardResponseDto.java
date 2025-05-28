package lt.javau12.TransferX.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lt.javau12.TransferX.enums.CardBrand;
import lt.javau12.TransferX.enums.CardType;


import java.time.LocalDate;


public class CardResponseDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CardType cardType;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CardBrand cardBrand;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String maskedCardNumber;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate expirationDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean isActive;

   public CardResponseDto(){

   }

    public CardResponseDto(Long id,
                           CardType cardType,
                           CardBrand cardBrand,
                           String maskedCardNumber,
                           LocalDate expirationDate,
                           boolean isActive) {

        this.id = id;
        this.cardType = cardType;
        this.cardBrand = cardBrand;
        this.maskedCardNumber = maskedCardNumber;
        this.expirationDate = expirationDate;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public CardBrand getCardBrand() {
        return cardBrand;
    }

    public void setCardBrand(CardBrand cardBrand) {
        this.cardBrand = cardBrand;
    }

    public String getMaskedCardNumber() {
        return maskedCardNumber;
    }

    public void setMaskedCardNumber(String maskedCardNumber) {
        this.maskedCardNumber = maskedCardNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @JsonProperty("isActive")
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
