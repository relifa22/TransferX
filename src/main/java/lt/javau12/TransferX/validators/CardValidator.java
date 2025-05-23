package lt.javau12.TransferX.validators;

import lt.javau12.TransferX.enums.AccountType;
import lt.javau12.TransferX.enums.CardType;
import org.springframework.stereotype.Component;

@Component
public class CardValidator {
    public CardType determinecardType(AccountType accountType){
        return switch (accountType){
            case ADULT -> CardType.ADULT;
            case TEENAGER -> CardType.TEENAGER;
            case CHILD -> CardType.CHILD;
        };
    }
}
