package lt.javau12.TransferX.DTO;


import jakarta.validation.constraints.*;
import lt.javau12.TransferX.enums.IncomeType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateUserDto {

    @NotBlank(message = "First name is required")
    private String name;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long and include an uppercase letter, a number, and a special character"
    )
    private String password;

    @PastOrPresent(message = "Birthdate cannot be in the future")
    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;

    @NotBlank(message = "Personal identification number is required")
    private String personalIdentificationNumber;

    @NotBlank(message = "Document number is required")
    private String documentNumber;

    @NotNull(message = "Monthly income is required")
    private BigDecimal monthlyIncome;

    @NotNull(message = "Income source must be specified")
    private IncomeType incomeType;

    @NotBlank(message = "Country must be specified")
    private String country;

    @NotBlank(message = "City must be specified")
    private String city;

    @NotBlank(message = "Address must be specified")
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
