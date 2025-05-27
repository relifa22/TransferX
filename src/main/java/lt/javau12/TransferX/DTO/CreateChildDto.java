package lt.javau12.TransferX.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lt.javau12.TransferX.enums.UserType;

import java.time.LocalDate;

public class CreateChildDto {

    @NotBlank(message = "First name is required")
    private String name;

    @NotBlank(message = "Last name is required")
    private String lastname;

    @PastOrPresent(message = "Birthdate cannot be in the future")
    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;

    @NotBlank(message = "Personal identification number is required")
    private String personalIdentificationNumber;

    @NotBlank(message = "Document number is required")
    private String documentNumber;

    @NotNull(message = "Parent ID is required")
    private Long parentId;



    public CreateChildDto(){

    }

    public CreateChildDto(String name,
                          String lastname,
                          LocalDate birthDate,
                          String personalIdentificationNumber,
                          String documentNumber,
                          Long parentId) {

        this.name = name;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.personalIdentificationNumber = personalIdentificationNumber;
        this.documentNumber = documentNumber;
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
