package lt.javau12.TransferX.mappers;


import lt.javau12.TransferX.DTO.CreateUserDto;
import lt.javau12.TransferX.DTO.UserDto;
import lt.javau12.TransferX.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User userEntity){
        return new UserDto(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getCountry(),
                userEntity.getCity(),
                userEntity.getAddress(),
                userEntity.getUserType()


        );
    }

    public User toEntity(CreateUserDto createUserDto){
        return new User(
                createUserDto.getName(),
                createUserDto.getLastName(),
                createUserDto.getEmail(),
                createUserDto.getPassword(),
                createUserDto.getBirthDate(),
                createUserDto.getPersonalIdentificationNumber(),
                createUserDto.getDocumentNumber(),
                createUserDto.getMonthlyIncome(),
                createUserDto.getIncomeType(),
                createUserDto.getCountry(),
                createUserDto.getCity(),
                createUserDto.getAddress()

        );
    }




}
