package lt.javau12.TransferX.DTO;

import java.util.List;

public class ClientFullInfoDto {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private List<AccountWithCardsDto> accountWithCardsDtos;
    private List<ChildListDto> childListDtos;


    public ClientFullInfoDto(Long id,
                             String name,
                             String lastName,
                             String email,
                             List<AccountWithCardsDto> accountWithCardsDtos,
                             List<ChildListDto> childListDtos) {

        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.accountWithCardsDtos = accountWithCardsDtos;
        this.childListDtos = childListDtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<AccountWithCardsDto> getAccountWithCardsDtos() {
        return accountWithCardsDtos;
    }

    public void setAccountWithCardsDtos(List<AccountWithCardsDto> accountWithCardsDtos) {
        this.accountWithCardsDtos = accountWithCardsDtos;
    }

    public List<ChildListDto> getChildListDtos() {
        return childListDtos;
    }

    public void setChildListDtos(List<ChildListDto> childListDtos) {
        this.childListDtos = childListDtos;
    }

}
