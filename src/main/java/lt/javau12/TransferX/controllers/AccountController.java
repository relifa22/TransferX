package lt.javau12.TransferX.controllers;

import jakarta.validation.Valid;
import lt.javau12.TransferX.DTO.AccountLimitDto;
import lt.javau12.TransferX.DTO.AccountResponseDto;
import lt.javau12.TransferX.DTO.CashViaCardDto;
import lt.javau12.TransferX.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // gaunama automatiskai sukurta saskaita
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
   @GetMapping("/default/{clientId}")
    public ResponseEntity<AccountResponseDto> getDefaultAccount(@PathVariable Long clientId){
        return ResponseEntity.ok(accountService.getDefaultAccountByClientId(clientId));
   }

   // rankiniu budu kuriama saskaita
   @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
   @PostMapping("/client/{clientId}")
   public ResponseEntity<AccountResponseDto> createAccountForClient(@PathVariable Long clientId){
       AccountResponseDto accountResponseDto = accountService.createAccountForClient(clientId);
       return ResponseEntity.status(201).body(accountResponseDto);
   }

   @PreAuthorize("hasRole('ADMIN')")
   @GetMapping
   public ResponseEntity<List<AccountResponseDto>> getAllAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
   }

    //pagal saskaitos id
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable Long id){
        return ResponseEntity.of(accountService.getAccountById(id));
   }

    // vartotojo saskaitos pagal id
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @GetMapping("/by-client/{clientId}")
    public ResponseEntity<List<AccountResponseDto>> getAllClientsAccountsByClientId(@PathVariable Long clientId){
        List<AccountResponseDto> accounts = accountService.getAccountsByClientId(clientId);
        return ResponseEntity.ok(accounts);
    }

   //grynųjų įnešimas per kortele
   @PreAuthorize("hasRole('CUSTOMER')")
   @PostMapping("/deposit")
   public ResponseEntity<AccountResponseDto> depositCashViaCard(@RequestBody CashViaCardDto cashDepositDto){
        AccountResponseDto accountResponseDto = accountService.depositCashToAccount(cashDepositDto);
        return ResponseEntity.ok(accountResponseDto);
   }

   //default limitai
   @PreAuthorize("hasRole('CUSTOMER')")
   @GetMapping("/limits/{accountId}")
   public ResponseEntity<AccountLimitDto> getDefaultLimits(@PathVariable Long accountId){
        AccountLimitDto limitDto = accountService.getDefaultAccountLimitsForAdults(accountId);
        return ResponseEntity.ok(limitDto);
   }

   //limitu nustatymas
   @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
   @PutMapping("/{accountId}/limits")
    public ResponseEntity<AccountLimitDto> updateTransferLimits(@PathVariable Long accountId,
                                                                @Valid @RequestBody AccountLimitDto accountLimitDto){
        AccountLimitDto updatedLimits = accountService.updateAccountLimits(accountId, accountLimitDto);
        return ResponseEntity.ok(updatedLimits);
   }

   // trinama saskaita neturėjusi transakcijų
   @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
   @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountId){
        String resultMessage = accountService.deleteAccountById(accountId);
        return ResponseEntity.ok(resultMessage);
   }



}
