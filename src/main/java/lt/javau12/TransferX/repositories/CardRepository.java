package lt.javau12.TransferX.repositories;

import lt.javau12.TransferX.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByNumber(String number);

}
