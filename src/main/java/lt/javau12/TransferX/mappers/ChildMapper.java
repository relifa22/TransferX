package lt.javau12.TransferX.mappers;

import lt.javau12.TransferX.DTO.ChildResponseDto;
import lt.javau12.TransferX.DTO.CreateChildDto;
import lt.javau12.TransferX.entities.User;
import org.springframework.stereotype.Component;

@Component
public class ChildMapper {

    public User toEntity(CreateChildDto createChildDto){
        User childUser = new User();
        childUser.setName(createChildDto.getName());
        childUser.setLastName(createChildDto.getLastname());
        childUser.setBirthDate(createChildDto.getBirthDate());
        childUser.setPersonalIdentificationNumber(createChildDto.getPersonalIdentificationNumber());
        return childUser;
    }

    public ChildResponseDto toChildDto(User user, String message){
        return new ChildResponseDto(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getUserType(),
                message,
                user.getCountry(),
                user.getCity(),
                user.getAddress()
        );

    }


}
