package lt.javau12.TransferX.repositories;


import lt.javau12.TransferX.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllBySenderAccountId(Long senderAccountId);

    List<Transaction> findAllBySenderAccountIdAndTimestampAfter(Long id, LocalDateTime date);

    List<Transaction> findAllByReceiverAccountIdAndTimestampAfter(Long id, LocalDateTime date);




}
