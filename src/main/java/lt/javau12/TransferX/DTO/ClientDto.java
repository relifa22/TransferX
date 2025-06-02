package lt.javau12.TransferX.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lt.javau12.TransferX.enums.ClientType;



public class ClientDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String name;
    private String lastName;
    private String email;
    private String country;
    private String city;
    private String address;
    private ClientType clientType;


    public ClientDto() {
    }

    public ClientDto(Long id,
                     String name,
                     String lastName,
                     String email,
                     String country,
                     String city,
                     String address,
                     ClientType clientType) {

        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.country = country;
        this.city = city;
        this.address = address;
        this.clientType = clientType;

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


    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }


}
