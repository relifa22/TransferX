package lt.javau12.TransferX.services;

import lt.javau12.TransferX.DTO.CardResponseDto;
import lt.javau12.TransferX.DTO.CreateCardDto;
import lt.javau12.TransferX.entities.Account;
import lt.javau12.TransferX.entities.Card;
import lt.javau12.TransferX.enums.CardType;
import lt.javau12.TransferX.formatters.CardNumberGenerator;
import lt.javau12.TransferX.formatters.CvvGenerator;
import lt.javau12.TransferX.mappers.CardMapper;
import lt.javau12.TransferX.repositories.AccountRepository;
import lt.javau12.TransferX.repositories.CardRepository;
import lt.javau12.TransferX.validators.CardValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    public CardResponseDto createCard(CreateCardDto createCardDto){
        Account account = accountRepository.findById(createCardDto.getAccountId())
                .orElseThrow(()-> new RuntimeException("Account not found"));

        String cardNumber = cardNumberGenerator.generateUniqeCardNumber();
        String cvv = cvvGenerator.generateCvv();
        LocalDate expirationDate = LocalDate.now().plusYears(4);

        CardType cardType = cardValidator.determinecardType(account.getAccountType());
        BigDecimal daylyLimit = cardType.getDailyLimit();
        BigDecimal weeklylimit = cardType.getWeeklyLimit();
        BigDecimal monthlylimit = cardType.getMonthlyLimit();

        Card card = cardMapper.toEntity(createCardDto,
                account,
                cardType,
                createCardDto.getCardBrand(),
                cardNumber,
                cvv,
                expirationDate,
                daylyLimit,
                weeklylimit,
                monthlylimit);
        Card savedCard = cardRepository.save(card);
        return cardMapper.toDto(savedCard);
    }






}
