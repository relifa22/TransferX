package lt.javau12.TransferX.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lt.javau12.TransferX.enums.UserType;

public class UserDto {

   @JsonProperty(access = JsonProperty.Access.READ_ONLY)
   private Long id;

   private String name;
   private String lastname;
   private String email;
   private boolean verified;
   private UserType userType;

   public UserDto(){

   }

    public UserDto(Long id, String name, String lastname, String email, boolean verified, UserType userType) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.verified = verified;
        this.userType = userType;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
