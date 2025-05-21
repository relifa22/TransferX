package lt.javau12.TransferX.DTO;


import jakarta.validation.constraints.Pattern;
import lt.javau12.TransferX.enums.IncomeType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateUserDto {
    private String name;
    private String lastName;
    private String email;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Slaptažodis turi būti bent 8 simbolių, su didžiąja raide, skaičiumi ir specialiu simboliu"
    )
    private String password;
    private LocalDate birthDate;
    private String personalIdentificationNumber;
    private String documentNumber;
    private BigDecimal monthlyIncome;
    private IncomeType incomeType;
    private String country;
    private String city;
    private String address;

    public CreateUserDto(){

    }

    public CreateUserDto(String name,
                         String lastName,
                         String email,
                         String password,
                         LocalDate birthDate,
                         String personalIdentificationNumber,
                         String documentNumber,
                         BigDecimal monthlyIncome,
                         IncomeType incomeType,
                         String country,
                         String city,
                         String address) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.personalIdentificationNumber = personalIdentificationNumber;
        this.documentNumber = documentNumber;
        this.monthlyIncome = monthlyIncome;
        this.incomeType = incomeType;
        this.country = country;
        this.city = city;
        this.address = address;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPersonalIdentificationNumber() {
        return personalIdentificationNumber;
    }

    public void setPersonalIdentificationNumber(String personalIdentificationNumber) {
        this.personalIdentificationNumber = personalIdentificationNumber;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public IncomeType getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(IncomeType incomeType) {
        this.incomeType = incomeType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
