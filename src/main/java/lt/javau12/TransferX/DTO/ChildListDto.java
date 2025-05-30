package lt.javau12.TransferX.DTO;

import java.util.List;

public class ChildListDto {

    private Long childId;
    private String firstName;
    private String lastName;
    private String personalIdentificationNumber;
    private List<AccountWithCardsDto> accountsWithcardsDtoList;

    public ChildListDto(){

    }

    public ChildListDto(Long childId,
                        String firstName,
                        String lastName,
                        String personalIdentificationNumber,
                        List<AccountWithCardsDto> accountsWithcardsDtoList) {

        this.childId = childId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalIdentificationNumber = personalIdentificationNumber;
        this.accountsWithcardsDtoList = accountsWithcardsDtoList ;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalIdentificationNumber() {
        return personalIdentificationNumber;
    }

    public void setPersonalIdentificationNumber(String personalIdentificationNumber) {
        this.personalIdentificationNumber = personalIdentificationNumber;
    }

    public List<AccountWithCardsDto> getAccountsWithcardsDtoList() {
        return accountsWithcardsDtoList;
    }

    public void setAccountsWithcardsDtoList(List<AccountWithCardsDto> accountsWithcardsDtoList) {
        this.accountsWithcardsDtoList = accountsWithcardsDtoList;
    }
}
