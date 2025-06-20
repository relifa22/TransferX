package lt.javau12.TransferX.DTO;

public class LoginWithAccountDto {

    private String jwt;
    private ClientDto clientDto;
    private AccountResponseDto accountResponseDto;

    public LoginWithAccountDto(){

    }

    public LoginWithAccountDto(String jwt,
                               ClientDto clientDto,
                               AccountResponseDto accountResponseDto) {
        this.jwt = jwt;

        this.clientDto = clientDto;
        this.accountResponseDto = accountResponseDto;
    }

    public ClientDto getClientDto() {
        return clientDto;
    }

    public void setClientDto(ClientDto clientDto) {
        this.clientDto = clientDto;
    }

    public AccountResponseDto getAccountResponseDto() {
        return accountResponseDto;
    }

    public void setAccountResponseDto(AccountResponseDto accountResponseDto) {
        this.accountResponseDto = accountResponseDto;
    }

    public String getJwt() {
        return jwt;
    }
}
