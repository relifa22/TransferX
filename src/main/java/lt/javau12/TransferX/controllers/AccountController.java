package lt.javau12.TransferX.controllers;

import jakarta.validation.Valid;
import lt.javau12.TransferX.DTO.AccountLimitDto;
import lt.javau12.TransferX.DTO.AccountListDto;
import lt.javau12.TransferX.DTO.AccountResponseDto;
import lt.javau12.TransferX.DTO.CardCashDepositDto;
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
   @GetMapping("/default/{clientId}")
    public ResponseEntity<AccountResponseDto> getDefaultAccount(@PathVariable Long clientId){
        return ResponseEntity.ok(accountService.getDefaultAccountByClientId(clientId));
   }

   // rankiniu budu kuriama saskaita
   @PostMapping("/client/{clientId}")
   public ResponseEntity<AccountResponseDto> createAccountForClient(@PathVariable Long clientId){
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
   @GetMapping("/by-client/{clientId}")
    public ResponseEntity<List<AccountResponseDto>> getAllClientsAccountsByClientId(@PathVariable Long clientId){
        List<AccountResponseDto> accounts = accountService.getAccountsByClientId(clientId);
        return ResponseEntity.ok(accounts);
   }

   //grynūjų įnešimas per kortele
   @PostMapping("/deposit")
   public ResponseEntity<AccountResponseDto> depositCashViaCard(@RequestBody CardCashDepositDto cashDepositDto){
        AccountResponseDto accountResponseDto = accountService.depositCashToAccount(cashDepositDto);
        return ResponseEntity.ok(accountResponseDto);
   }

   //limitu nustatymas
   @PutMapping("/{accountId}/limits")
    public ResponseEntity<AccountLimitDto> updateTransferLimits(@PathVariable Long accountId,
                                                                @Valid @RequestBody AccountLimitDto accountLimitDto){
        AccountLimitDto updatedLimits = accountService.updateAccountLimits(accountId, accountLimitDto);
        return ResponseEntity.ok(updatedLimits);
   }


   // trinama saskaita neturėjusi transakcijų
   @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountId){
        String resultMessage = accountService.deleteAccountById(accountId);
        return ResponseEntity.ok(resultMessage);
   }



}
