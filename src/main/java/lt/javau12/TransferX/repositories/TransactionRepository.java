package lt.javau12.TransferX.repositories;

import lt.javau12.TransferX.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
