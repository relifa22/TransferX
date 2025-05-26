package lt.javau12.TransferX.formatters;


import lt.javau12.TransferX.repositories.CardRepository;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class CardNumberGenerator {

    private final SecureRandom secureRandom = new SecureRandom();
    private final CardRepository cardRepository;

    public CardNumberGenerator(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public String generateUniqeCardNumber(){
        String number;
        do {
            number = generateCardNumber();
        }while (cardRepository.existsByCardNumber(number));
        return number;
    }

    private String generateCardNumber(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 16; i++){
            stringBuilder.append(secureRandom.nextInt(10));
        }
        return stringBuilder.toString();
    }
}
