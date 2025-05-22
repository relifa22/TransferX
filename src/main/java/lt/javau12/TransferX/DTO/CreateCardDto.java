package lt.javau12.TransferX.DTO;


import jakarta.validation.constraints.NotNull;
import lt.javau12.TransferX.enums.CardBrand;
import lt.javau12.TransferX.enums.CardType;


public class CreateCardDto {

    @NotNull(message = "Card type must be selected")
    private CardType cardType;

    @NotNull(message = "Card brand must be selected")
    private CardBrand cardBrand;

    @NotNull(message = "Account ID is required")
    private Long accountId;


    public CreateCardDto(){

    }

    public CreateCardDto(CardType cardType,
                         CardBrand cardBrand,
                         Long accountId) {

        this.cardType = cardType;
        this.cardBrand = cardBrand;
        this.accountId = accountId;
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


    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
