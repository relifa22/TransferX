package lt.javau12.TransferX.controllers;


import jakarta.validation.Valid;
import lt.javau12.TransferX.DTO.LoginDto;
import lt.javau12.TransferX.DTO.LoginResponseDto;
import lt.javau12.TransferX.DTO.LoginWithAccountDto;
import lt.javau12.TransferX.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto){
        LoginResponseDto responseDto = authService.login(loginDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("login/full")
    public ResponseEntity<LoginWithAccountDto> loginWithAccount(@Valid @RequestBody LoginDto loginDto){
        LoginWithAccountDto login = authService.loginWithAccount(loginDto);
        return ResponseEntity.ok(login);
    }


}
