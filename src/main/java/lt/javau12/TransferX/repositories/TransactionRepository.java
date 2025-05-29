package lt.javau12.TransferX.repositories;

import lt.javau12.TransferX.entities.Account;
import lt.javau12.TransferX.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllBySenderAccountId(Long senderAccountId);



}
