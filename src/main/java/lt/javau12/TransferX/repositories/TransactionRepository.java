package lt.javau12.TransferX.repositories;


import lt.javau12.TransferX.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllBySenderAccountId(Long senderAccountId);

    List<Transaction> findAllBySenderAccountIdAndTimestampAfter(Long id, LocalDateTime date);

    List<Transaction> findAllByReceiverAccountIdAndTimestampAfter(Long id, LocalDateTime date);


    // Dienos limito tikrinimui
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
            "WHERE t.senderAccount.id = :accountId AND DATE(t.timestamp) = :date")
    BigDecimal sumAmountBySenderAndDate(@Param("accountId") Long accountId,
                                        @Param("date") LocalDate date);

    // Mėnesio limito tikrinimui
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
            "WHERE t.senderAccount.id = :accountId AND DATE(t.timestamp) BETWEEN :start AND :end")
    BigDecimal sumAmountBySenderBetweenDates(@Param("accountId") Long accountId,
                                             @Param("start") LocalDate start,
                                             @Param("end") LocalDate end);


}
