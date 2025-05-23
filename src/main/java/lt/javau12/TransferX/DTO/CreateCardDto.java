package lt.javau12.TransferX.DTO;


import jakarta.validation.constraints.NotNull;
import lt.javau12.TransferX.enums.CardBrand;
import lt.javau12.TransferX.enums.CardType;


public class CreateCardDto {


    @NotNull(message = "Card brand must be selected")
    private CardBrand cardBrand;

    @NotNull(message = "Account ID is required")
    private Long accountId;


    public CreateCardDto(){

    }

    public CreateCardDto(CardBrand cardBrand,
                         Long accountId) {

        this.cardBrand = cardBrand;
        this.accountId = accountId;
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
