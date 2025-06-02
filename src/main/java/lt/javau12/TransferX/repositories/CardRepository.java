package lt.javau12.TransferX.repositories;
import java.util.List;
import java.util.Optional;

import lt.javau12.TransferX.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

     boolean existsByCardNumber(String cardNumber);

     List<Card> findAllByAccountId(Long accountId);

     Optional<Card> findByAccountId(Long accountId);




}
