package lt.javau12.TransferX.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lt.javau12.TransferX.enums.UserType;

public class ChildResponseDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String name;
    private String lastName;
    private UserType userType;
    private String message;
    private String country;
    private String city;
    private String address;

    public ChildResponseDto(){

    }

    public ChildResponseDto(Long id,
                            String name,
                            String lastName,
                            UserType userType, String message,
                            String country,
                            String city,
                            String address) {

        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.userType = userType;
        this.message = message;
        this.country = country;
        this.city = city;
        this.address = address;
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

    public UserType getUserType() {
        return userType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
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

