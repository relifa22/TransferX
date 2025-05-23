package lt.javau12.TransferX.validators;

import lt.javau12.TransferX.enums.UserType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class UserValidator {

    //
    public boolean doesPersonalCodeMatchBirthday(String personalCode, LocalDate birthDate){

        if (personalCode == null
                || personalCode.length() < 7
                || birthDate == null){
            return false;
        }

        String codeYear = personalCode.substring(1, 3);
        String codeMonth = personalCode.substring(3, 5);
        String codeDate = personalCode.substring(5, 7);

        String birthYear = String.format("%02d", birthDate.getYear() % 100);
        String birthMonth = String.format("%02d", birthDate.getMonthValue());
        String birthDay = String.format("%02d", birthDate.getDayOfMonth());

        return codeYear.equals(birthYear)
                && codeMonth.equals(birthMonth)
                && codeDate.equals(birthDay);
    }

    public boolean isAdult(LocalDate birthDate){
        if (birthDate == null){
            return false;
        }

        int age = Period.between(birthDate, LocalDate.now()).getYears();
        return age >= 18;
    }

    // userio tipo nustatymas
    public UserType determineUserType(LocalDate birthDate) {
        if (birthDate == null) {
            throw new RuntimeException("BirthDate is required");
        }

        int age = Period.between(birthDate, LocalDate.now()).getYears();

        if (age < 13) return UserType.CHILD;
        if (age < 18) return UserType.TEENAGER;
        return UserType.ADULT;
    }



}
