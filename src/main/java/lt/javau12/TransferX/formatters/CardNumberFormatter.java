package lt.javau12.TransferX.formatters;

import org.springframework.stereotype.Component;

@Component
public class CardNumberFormatter {

    public String maskCardNumber(String number){
        return (number == null || number.length() < 4)
                ? "****"
                : "**** **** ****" + number.substring(number.length() - 4);
    }
}
