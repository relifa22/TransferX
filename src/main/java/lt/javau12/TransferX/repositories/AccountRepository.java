package lt.javau12.TransferX.repositories;

import lt.javau12.TransferX.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByIban(String iban);

    List<Account> findByClientId(Long clientId);

    Optional<Account> findFirstByClientId(Long clientId);

    Optional<Account> findByIban(String iban);

    Optional<Account> findDefaultAccountByClientId(Long clientId);




}
