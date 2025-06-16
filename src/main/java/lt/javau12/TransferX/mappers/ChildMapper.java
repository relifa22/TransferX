package lt.javau12.TransferX.mappers;

import lt.javau12.TransferX.DTO.ChildResponseDto;
import lt.javau12.TransferX.DTO.CreateChildDto;
import lt.javau12.TransferX.entities.Client;
import org.springframework.stereotype.Component;

@Component
public class ChildMapper {

    public Client toEntity(CreateChildDto createChildDto){
        Client childClient = new Client();
        childClient.setName(createChildDto.getName());
        childClient.setLastName(createChildDto.getLastName());
        childClient.setBirthDate(createChildDto.getBirthDate());
        childClient.setPersonalIdentificationNumber(createChildDto.getPersonalIdentificationNumber());
        return childClient;
    }

    public ChildResponseDto toChildDto(Client client, String message){
        return new ChildResponseDto(
                client.getId(),
                client.getName(),
                client.getLastName(),
                client.getClientType(),
                message,
                client.getCountry(),
                client.getCity(),
                client.getAddress()
        );

    }


}
