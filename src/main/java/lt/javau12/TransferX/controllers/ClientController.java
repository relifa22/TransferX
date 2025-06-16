package lt.javau12.TransferX.controllers;

import jakarta.validation.Valid;
import lt.javau12.TransferX.DTO.*;
import lt.javau12.TransferX.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping ("/api/clients")
public class ClientController {

    private final ClientService clientService;


    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    //kliento kurimas
    @PostMapping
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody CreateClientDto createClientDto){
        ClientDto created = clientService.createClient(createClientDto);
        return ResponseEntity.status(201).body(created);

    }

    // child kliento kurimas
    @PostMapping("/child")
    public ResponseEntity<ChildResponseDto> createChildClient(@Valid @RequestBody CreateChildDto createChildDto){
        ChildResponseDto created = clientService.createChildClient(createChildDto);
        return ResponseEntity.status(201).body(created);

    }

    //visi klientai
    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients(){
        return ResponseEntity.ok(clientService.getAllClients());
    }

    // klientas pagal id
    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable Long id){
        return ResponseEntity.of(clientService.getClientById(id));

    }

    //vaikai pagal parent id
    @GetMapping("/parent/{parentId}/children")
    public ResponseEntity<List<ChildListDto>> getAllChildByParentId(@PathVariable Long parentId){
        return ResponseEntity.ok(clientService.getChildrenByParentId(parentId));
    }

    // pilna info apie klienta
    @GetMapping("/{clientId}/full-info")
    public ResponseEntity<ClientFullInfoDto> getClientFullInfo(@PathVariable Long clientId){
        ClientFullInfoDto fullInfoDto = clientService.getFullInfoByClientId(clientId);
        return ResponseEntity.ok(fullInfoDto);
    }

    //pilna info apie visus userius
    @GetMapping("/full-info")
    public ResponseEntity<List<ClientFullInfoDto>> getAllClientsFullInfo() {
        return ResponseEntity.ok(clientService.getAllClientsFullInfo());
    }

    //adreso keitimas
    @PutMapping("/address")
    public ResponseEntity<ClientDto> updateAddress(@RequestBody UpdateAddressDto updateAddressDto){
        return ResponseEntity.ok(clientService.updateAddress(updateAddressDto));
    }

    // Vaiko trynimas leidžiamas tik jei jis neturėjo jokių transakcijų
    // Šiuo metu atliekamas galutinis trynimas - hard delete
    @DeleteMapping("/child/{id}")
    public ResponseEntity<Void> deleteChildById(@PathVariable("id") Long childId){
        boolean childDeleted = clientService.deleteChildById(childId);
        return childDeleted
                ?ResponseEntity.noContent().build()
                :ResponseEntity.notFound().build();
    }

    // Suaugęs vartotojas gali būti ištrintas tik jei neturėjo transakcijų
    // Sąskaitos automatiškai ištrinamos kartu
    // Šiuo metu trynimas yra galutinis - hard delete
    @DeleteMapping("/adult/{id}")
    public ResponseEntity<Void> deleteAdultById(@PathVariable Long id){
        boolean deleted = clientService.deleteAdultById(id);
        return deleted
                ?ResponseEntity.noContent().build()
                :ResponseEntity.notFound().build();
    }


}
