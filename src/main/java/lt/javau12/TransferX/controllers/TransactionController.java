package lt.javau12.TransferX.controllers;

import jakarta.validation.Valid;
import lt.javau12.TransferX.DTO.TransactionResponseDto;
import lt.javau12.TransferX.DTO.TransferRequestDto;
import lt.javau12.TransferX.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/send")
    public ResponseEntity<TransactionResponseDto> sendMoney( @Valid @RequestBody TransferRequestDto request){
        TransactionResponseDto responseDto = transactionService.sendMoney(request);
        return ResponseEntity.status(201).body(responseDto);
    }

    @GetMapping("/history")
    public ResponseEntity<List<TransactionResponseDto>> getHistoy(@RequestParam String iban, @RequestParam String period){
        return ResponseEntity.ok(transactionService.getHistoryByIbanAndPeiod(iban, period));

    }
}
