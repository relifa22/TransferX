package lt.javau12.TransferX.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginDto {


    @NotBlank(message = "El. paštas privalomas")
    @Email(message = "Neteisingas el. pašto formatas")
    private String email;

    private String password;

}
