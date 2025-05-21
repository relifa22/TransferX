package lt.javau12.TransferX.repositories;

import lt.javau12.TransferX.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
