package lt.javau12.TransferX.DTO;

import java.util.List;

public class AccountWithCardsDto {
    private String iban;
    private List<CardResponseDto> cardResponseDtoList;

    public AccountWithCardsDto(){}

    public AccountWithCardsDto(String iban, List<CardResponseDto> cardResponseDtoList) {
        this.iban = iban;
        this.cardResponseDtoList = cardResponseDtoList;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public List<CardResponseDto> getCardResponseDtoList() {
        return cardResponseDtoList;
    }

    public void setCardResponseDtoList(List<CardResponseDto> cardResponseDtoList) {
        this.cardResponseDtoList = cardResponseDtoList;
    }
}
