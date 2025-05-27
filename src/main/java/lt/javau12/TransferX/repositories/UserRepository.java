package lt.javau12.TransferX.repositories;


import lt.javau12.TransferX.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    boolean existsByPersonalIdentificationNumber(String personalIdentificationNumber);
    List<User> findAllByParentId(Long parentId);
}
