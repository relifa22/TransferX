package lt.javau12.TransferX.repositories;

import lt.javau12.TransferX.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
