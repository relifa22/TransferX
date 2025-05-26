package lt.javau12.TransferX.formatters;

import lt.javau12.TransferX.repositories.AccountRepository;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;


@Component
public class IbanGenerator {

    private final AccountRepository accountRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    private String countryCode = "LT";
    private String bankCode = "90909";
    private int accountNumberLength = 11;


    public IbanGenerator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String generateUniqueIban(){
        String iban;
        do {
            iban = generateIban();
        }while (accountRepository.existsByIban(iban));
        return iban;
    }

    public String generateIban() {
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i <accountNumberLength; i++){
            accountNumber.append(secureRandom.nextInt(10));
        }
        return countryCode + bankCode + accountNumber;
    }
}
