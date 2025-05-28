package lt.javau12.TransferX.validators;

import lt.javau12.TransferX.enums.UserType;
import lt.javau12.TransferX.exeptions.ValidationException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class UserValidator {

    //tikrinama ar asmens kodas ir gimimo data sutampa
    public void doesPersonalCodeMatchBirthday(String personalCode, LocalDate birthDate){

        if (personalCode == null
                || birthDate == null
                || personalCode.length() < 7){
            throw new ValidationException("Personal identification code or birthdate is not correct");
        }

        String codeYear = personalCode.substring(1, 3);
        String codeMonth = personalCode.substring(3, 5);
        String codeDate = personalCode.substring(5, 7);

        String birthYear = String.format("%02d", birthDate.getYear() % 100);
        String birthMonth = String.format("%02d", birthDate.getMonthValue());
        String birthDay = String.format("%02d", birthDate.getDayOfMonth());

        System.out.println("codeYear: " + codeYear + " vs birthYear: " + birthYear);
        System.out.println("codeMonth: " + codeMonth + " vs birthMonth: " + birthMonth);
        System.out.println("codeDate: " + codeDate + " vs birthDay: " + birthDay);

        if (!codeYear.equals(birthYear)
                || !codeMonth.equals(birthMonth)
                || !codeDate.equals(birthDay)) {
            throw new ValidationException("Personal identification code does not match birth date. Please check your inputs.");
        }
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

    public void validateSelectedTypeAgainstBirthDate(UserType selectedType, LocalDate birthdate){
        if (selectedType == UserType.ADULT && !isAdult(birthdate)){
            throw new ValidationException("child cannot have adult account. Please check birthdate or choose other user type.");
        }
    }



}
