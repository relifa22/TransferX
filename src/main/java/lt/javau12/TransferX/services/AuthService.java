package lt.javau12.TransferX.services;

import lt.javau12.TransferX.DTO.*;
import lt.javau12.TransferX.entities.Account;
import lt.javau12.TransferX.entities.Client;
import lt.javau12.TransferX.exeptions.NotFoundException;
import lt.javau12.TransferX.exeptions.ValidationException;
import lt.javau12.TransferX.mappers.AccountMapper;
import lt.javau12.TransferX.mappers.ClientMapper;
import lt.javau12.TransferX.repositories.AccountRepository;
import lt.javau12.TransferX.repositories.ClientRepository;
import lt.javau12.TransferX.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder encoder;
    private final ClientMapper clientMapper;
    private final AccountMapper accountMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthService(ClientRepository clientRepository,
                       AccountRepository accountRepository,
                       PasswordEncoder encoder,
                       ClientMapper clientMapper,
                       AccountMapper accountMapper,
                       AuthenticationManager authenticationManager,
                       JwtUtils jwtUtils) {

        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.encoder = encoder;
        this.clientMapper = clientMapper;
        this.accountMapper = accountMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public LoginResponseDto login(LoginDto loginDto){
        Client client = clientRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new ValidationException("Invalid email or password"));

        if (!encoder.matches(loginDto.getPassword(), client.getPassword())){
            throw new ValidationException("invalid email or password");
        }

        return new LoginResponseDto(
                client.getId(),
                client.getName(),
                client.getLastName(),
                client.getEmail()

        );
    }
    public LoginWithAccountDto loginWithAccount(LoginDto loginDto){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken( loginDto.getEmail(), loginDto.getPassword()));

        Client client = clientRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new NotFoundException("client not found by email."));

        if (!encoder.matches(loginDto.getPassword(), client.getPassword())){
            throw new ValidationException("invalid email or password.");
        }

        Account account = accountRepository.findDefaultAccountByClientId(client.getId())
                .orElseThrow(() -> new NotFoundException("account not found"));

        String jwt = jwtUtils.generateToken(client.getEmail());

        ClientDto clientDto = clientMapper.toDto(client);
        AccountResponseDto accountResponseDto = accountMapper.toDto(account);

        return new LoginWithAccountDto(jwt, clientDto, accountResponseDto);
    }


}
