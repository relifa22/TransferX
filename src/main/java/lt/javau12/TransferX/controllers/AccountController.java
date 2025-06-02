package lt.javau12.TransferX.controllers;

import jakarta.validation.Valid;
import lt.javau12.TransferX.DTO.AccountLimitDto;
import lt.javau12.TransferX.DTO.AccountListDto;
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
    public ResponseEntity<AccountResponseDto> getDefaultAccount(@PathVariable Long clientId){
        return ResponseEntity.ok(accountService.getDefaultAccountByClientId(clientId));
   }

   // rankiniu budu kuriama saskaita
   @PostMapping("/user/{Id}")
   public ResponseEntity<AccountResponseDto> createAccountForUser(@PathVariable Long clientId){
       AccountResponseDto accountResponseDto = accountService.createAccountForClient(clientId);
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
    public ResponseEntity<List<AccountResponseDto>> getAllUsersAccountsByUserId(@PathVariable Long clientId){
        List<AccountResponseDto> accounts = accountService.getAccountsByClientId(clientId);
        return ResponseEntity.ok(accounts);
   }

   @PutMapping("/{accountId}/limits")
    public ResponseEntity<AccountLimitDto> updateTransferlimits(@PathVariable Long accountId,
                                                                @Valid @RequestBody AccountLimitDto accountLimitDto){
        AccountLimitDto updatedLimits = accountService.updateAccountLimits(accountId, accountLimitDto);
        return ResponseEntity.ok(updatedLimits);
   }



}
