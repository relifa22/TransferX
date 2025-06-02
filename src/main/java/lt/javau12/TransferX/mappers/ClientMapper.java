package lt.javau12.TransferX.mappers;


import lt.javau12.TransferX.DTO.CreateClientDto;
import lt.javau12.TransferX.DTO.ClientDto;
import lt.javau12.TransferX.entities.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientDto toDto(Client clientEntity){
        return new ClientDto(
                clientEntity.getId(),
                clientEntity.getName(),
                clientEntity.getLastName(),
                clientEntity.getEmail(),
                clientEntity.getCountry(),
                clientEntity.getCity(),
                clientEntity.getAddress(),
                clientEntity.getClientType()


        );
    }

    public Client toEntity(CreateClientDto createClientDto){
        return new Client(
                createClientDto.getName(),
                createClientDto.getLastName(),
                createClientDto.getEmail(),
                createClientDto.getPassword(),
                createClientDto.getBirthDate(),
                createClientDto.getPersonalIdentificationNumber(),
                createClientDto.getDocumentNumber(),
                createClientDto.getMonthlyIncome(),
                createClientDto.getIncomeType(),
                createClientDto.getCountry(),
                createClientDto.getCity(),
                createClientDto.getAddress()

        );
    }




}
