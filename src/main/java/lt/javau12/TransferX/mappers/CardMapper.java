package lt.javau12.TransferX.mappers;

import lt.javau12.TransferX.DTO.CardResponseDto;
import lt.javau12.TransferX.DTO.CreateCardDto;
import lt.javau12.TransferX.entities.Account;
import lt.javau12.TransferX.entities.Card;
import lt.javau12.TransferX.formatters.CardNumberFormatter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class CardMapper {

    private final CardNumberFormatter cardNumberFormatter;

    public CardMapper(CardNumberFormatter cardNumberFormatter) {
        this.cardNumberFormatter = cardNumberFormatter;
    }

    public CardResponseDto toDto(Card cardEntity){
        return new CardResponseDto(
                cardEntity.getId(),
                cardEntity.getCardType(),
                cardEntity.getCardBrand(),
                cardNumberFormatter.maskCardNumber(cardEntity.getCardNumber()),
                cardEntity.getExpirationDate(),
                cardEntity.isActive()



        );
    }

    public Card toEntity(CreateCardDto createCardDto, Account account,
                         String cardNumber, String cvv,
                         LocalDate expirationDate,
                         BigDecimal dailyLimit,
                         BigDecimal weeklyLimit,
                         BigDecimal monthlyLimit) {

        Card card = new Card();
        card.setCardType(createCardDto.getCardType());
        card.setCardBrand(createCardDto.getCardBrand());
        card.setAccount(account);
        card.setCardNumber(cardNumber);
        card.setCvv(cvv);
        card.setExpirationDate(expirationDate);
        card.setDailySpendingLimit(dailyLimit);
        card.setWeeklySpendingLimit(weeklyLimit);
        card.setMonthlySpendingLimit(monthlyLimit);
        card.setActive(false);

        return card;
    }


}
