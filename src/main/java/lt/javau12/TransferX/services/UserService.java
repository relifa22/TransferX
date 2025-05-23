package lt.javau12.TransferX.services;

import lt.javau12.TransferX.DTO.CreateUserDto;
import lt.javau12.TransferX.DTO.UserDto;
import lt.javau12.TransferX.entities.User;
import lt.javau12.TransferX.enums.UserType;
import lt.javau12.TransferX.mappers.UserMapper;
import lt.javau12.TransferX.repositories.UserRepository;
import lt.javau12.TransferX.validators.UserValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AccountService accountService;
    private final UserValidator userValidator;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       AccountService accountService,
                       UserValidator userValidator) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.accountService = accountService;
        this.userValidator = userValidator;
    }

    // sukuriamas naujas vartotojas
    public UserDto createUser(CreateUserDto createUserDto){
        User user = userMapper.toEntity(createUserDto);
        user.setPassword(createUserDto.getPassword());

        // tikrinam ar gimimo data ir ak sutampa
        if (!userValidator.doesPersonalCodeMatchBirthday(user.getPersonalIdentificationNumber(),
                user.getBirthDate())){
            throw new RuntimeException("Personal identification number and birthDate do not match");

        }

        //nustatomas vartotojo tipas pagal metus
        user.setUserType(userValidator.determineUserType(user.getBirthDate()));

        User savedUser = userRepository.save(user);

        //automatinis saskaitos sukurimas pilnameciui
        if (savedUser.getUserType() == UserType.ADULT){
            accountService.createDefaultAccountForUser(savedUser);
        }
        return userMapper.toDto(savedUser);
    }

    //visi vartotojai
    public List<UserDto> getAllUsers(){
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }
    // vartotojas pagal id
    public Optional<UserDto> getUserById(Long id){
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }




}
