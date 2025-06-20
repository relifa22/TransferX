package lt.javau12.TransferX.services;

import lt.javau12.TransferX.entities.Client;
import lt.javau12.TransferX.repositories.ClientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;

    public ClientDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Client not found by email"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(client.getEmail())
                .password(client.getPassword())
                .authorities("ROLE_" + client.getRole().name())
                .disabled(!client.isVerified())
                .build();
    }



}
