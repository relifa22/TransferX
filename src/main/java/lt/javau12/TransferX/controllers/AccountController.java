package lt.javau12.TransferX.controllers;

import lt.javau12.TransferX.DTO.AccountResponseDto;
import lt.javau12.TransferX.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // gaunama automatiskai sukurta saskaita
   @GetMapping("/default/{userId}")
    public ResponseEntity<AccountResponseDto> getDefaultAccount(@PathVariable Long userId){
        return ResponseEntity.ok(accountService.getDefaultAccountByUserId(userId));
   }

   // rankiniu budu kuriama saskaita
   @PostMapping("/user/{Id}")
   public ResponseEntity<AccountResponseDto> createAccountForUser(@PathVariable Long userId){
       AccountResponseDto accountResponseDto = accountService.createAccountForUser(userId);
       return ResponseEntity.status(201).body(accountResponseDto);
   }

   @GetMapping
   public ResponseEntity<List<AccountResponseDto>> getAllAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
   }

    //pagal saskaitos id
   @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable Long id){
        return ResponseEntity.of(accountService.getAccountById(id));
   }

    // vartotojo saskaitos pagal id
   @GetMapping("/api/accounts/by-user/{userId}")
    public ResponseEntity<List<AccountResponseDto>> getAllUsersAccountsByUserId(@PathVariable Long userId){
        List<AccountResponseDto> accounts = accountService.getAccountsByUserId(userId);
        return ResponseEntity.ok(accounts);
   }



}
