package lt.javau12.TransferX.controllers;

import lt.javau12.TransferX.DTO.CardResponseDto;
import lt.javau12.TransferX.services.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    // suaugusio korteles kurimas
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/account/{accountId}")
    public ResponseEntity<CardResponseDto> createCardForAdult(@PathVariable Long accountId){
        CardResponseDto createdAdultCard = cardService.createCardForAdult(accountId);
        return ResponseEntity.status(201).body(createdAdultCard);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<CardResponseDto>> getAllCards(){
        return ResponseEntity.ok(cardService.getAllCard());
    }

    // kortele pagal id
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDto> getCardById(@PathVariable Long id){
        return ResponseEntity.ok(cardService.getCardById(id));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/by-account/{accountId}")
    public ResponseEntity<CardResponseDto> getCardByAccountId(@PathVariable Long accountId){
        return ResponseEntity.of(cardService.getCardByAccountId(accountId));
    }

    //Korteles aktyvavimas
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @PatchMapping("/{cardId}/activate")
    public ResponseEntity<CardResponseDto> activateCard(@PathVariable Long cardId){
        CardResponseDto responseDto = cardService.activateCard(cardId);
        return ResponseEntity.ok(responseDto);
    }
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @PatchMapping("/{cardId}/deactivate")
    public ResponseEntity<Void> deactivateCard(@PathVariable Long cardId) {
        boolean deactivated = cardService.deactivateCard(cardId);
        return deactivated
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // korteles istrynimas
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id){
        boolean deleted = cardService.deleteCard(id);
        return deleted
                ? ResponseEntity.noContent().build()
                :ResponseEntity.notFound().build();
    }



}
