package lt.javau12.TransferX.controllers;

import lt.javau12.TransferX.DTO.AccountResponseDto;
import lt.javau12.TransferX.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

   @GetMapping("/default/{userId}")
    public ResponseEntity<AccountResponseDto> getDefaultAccount(@PathVariable Long userId){
        return ResponseEntity.ok(accountService.getDefaultAccountByUserId(userId));
   }


}
