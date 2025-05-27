package lt.javau12.TransferX.controllers;

import lt.javau12.TransferX.DTO.CardResponseDto;
import lt.javau12.TransferX.services.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("create/account/{accountId}")
    public ResponseEntity<CardResponseDto> createCardForAdult(@PathVariable Long accountId){
        CardResponseDto createdAdultCard = cardService.createCardForAdult(accountId);
        return ResponseEntity.status(201).body(createdAdultCard);
    }

}
