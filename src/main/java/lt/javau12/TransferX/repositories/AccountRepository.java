package lt.javau12.TransferX.repositories;

import lt.javau12.TransferX.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByIban(String iban);

    List<Account> findByUserId(Long userId);
}
