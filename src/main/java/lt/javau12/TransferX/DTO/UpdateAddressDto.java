package lt.javau12.TransferX.DTO;

public class UpdateAddressDto {

    private Long clientId;
    private String country;
    private String city;
    private String address;

    public UpdateAddressDto(){

    }

    public UpdateAddressDto(Long clientId,
                            String country,
                            String city,
                            String address) {

        this.clientId = clientId;
        this.country = country;
        this.city = city;
        this.address = address;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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
