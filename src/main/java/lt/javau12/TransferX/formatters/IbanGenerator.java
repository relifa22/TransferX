package lt.javau12.TransferX.formatters;

import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public class IbanGenerator {

    private String countryCode = "LT";
    private String bankCode = "90909";
    private int accountNumberLength = 11;

    public String generateIban(){
        StringBuilder accountNumber = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < accountNumberLength; i++){
            accountNumber.append(random.nextInt(10));
        }
        return countryCode + bankCode + accountNumber;
    }




}
