package lt.javau12.TransferX.DTO;

public class UpdateAddressDto {

    private Long userId;
    private String country;
    private String city;
    private String address;

    public UpdateAddressDto(){

    }

    public UpdateAddressDto(Long userId,
                            String country,
                            String city,
                            String address) {

        this.userId = userId;
        this.country = country;
        this.city = city;
        this.address = address;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
