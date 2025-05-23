package lt.javau12.TransferX.formatters;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CvvGenerator {
    private final Random random = new Random();

    public String generateCvv(){
        int number = random.nextInt(1000);
        return String.format("%03d", number);
    }

}
