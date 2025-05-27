package lt.javau12.TransferX.services;

import lt.javau12.TransferX.DTO.CardResponseDto;
import lt.javau12.TransferX.entities.Account;
import lt.javau12.TransferX.entities.Card;
import lt.javau12.TransferX.enums.AccountType;
import lt.javau12.TransferX.enums.CardBrand;
import lt.javau12.TransferX.enums.CardType;
import lt.javau12.TransferX.exeptions.NotFoundExeption;
import lt.javau12.TransferX.exeptions.ValidationException;
import lt.javau12.TransferX.formatters.CardNumberGenerator;
import lt.javau12.TransferX.formatters.CvvGenerator;
import lt.javau12.TransferX.mappers.CardMapper;
import lt.javau12.TransferX.repositories.AccountRepository;
import lt.javau12.TransferX.repositories.CardRepository;
import lt.javau12.TransferX.validators.CardValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class CardService {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final CardNumberGenerator cardNumberGenerator;
    private final CvvGenerator cvvGenerator;
    private final AccountRepository accountRepository;
    private final CardValidator cardValidator;


    public CardService(CardRepository cardRepository,
                       CardMapper cardMapper,
                       CardNumberGenerator cardNumberGenerator,
                       CvvGenerator cvvGenerator,
                       AccountRepository accountRepository,
                       CardValidator cardValidator) {

        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
        this.cardNumberGenerator = cardNumberGenerator;
        this.cvvGenerator = cvvGenerator;
        this.accountRepository = accountRepository;
        this.cardValidator = cardValidator;
    }


    public CardResponseDto createDefaultCardForChildAccount(Long accountId){

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundExeption("Account not found with id: " + accountId));

        CardType cardType = cardValidator.determinecardType(account.getAccountType());
        String cardNumber = cardNumberGenerator.generateUniqeCardNumber();
        String cvv = cvvGenerator.generateCvv();
        LocalDate expirationDate = LocalDate.now().plusYears(4);

        Card card = new Card();
        card.setAccount(account);
        card.setCardType(cardType);
        card.setCardBrand(CardBrand.MASTERCARD);
        card.setCardNumber(cardNumber);
        card.setCvv(cvv);
        card.setExpirationDate(expirationDate);
        card.setDailySpendingLimit(cardType.getDailyLimit());
        card.setWeeklySpendingLimit(cardType.getWeeklyLimit());
        card.setMonthlySpendingLimit(cardType.getMonthlyLimit());

        Card saved = cardRepository.save(card);
        return cardMapper.toDto(saved);

    }

    // rankinis korteles sukurimas suaugusiam
    public CardResponseDto createCardForAdult(Long accountId) {
        return Optional.ofNullable(accountRepository.findById(accountId)
                        .orElseThrow(() -> new NotFoundExeption("Account not found with id: " + accountId)))
                .filter(acc -> acc.getAccountType() == AccountType.ADULT)
                .map(account -> {
                    CardType cardType = cardValidator.determinecardType(account.getAccountType());
                    String cardNumber = cardNumberGenerator.generateUniqeCardNumber();
                    String cvv = cvvGenerator.generateCvv();
                    LocalDate expirationDate = LocalDate.now().plusYears(4);

                    Card card = new Card();
                    card.setAccount(account);
                    card.setCardType(cardType);
                    card.setCardBrand(CardBrand.VISA);
                    card.setCardNumber(cardNumber);
                    card.setCvv(cvv);
                    card.setExpirationDate(expirationDate);
                    card.setDailySpendingLimit(cardType.getDailyLimit());
                    card.setWeeklySpendingLimit(cardType.getWeeklyLimit());
                    card.setMonthlySpendingLimit(cardType.getMonthlyLimit());

                    return cardMapper.toDto(cardRepository.save(card));
                })
                .orElseThrow(() -> new ValidationException("Card creation allowed only for adult accounts."));
    }


}
