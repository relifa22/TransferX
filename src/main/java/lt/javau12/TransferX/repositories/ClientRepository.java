package lt.javau12.TransferX.repositories;


import lt.javau12.TransferX.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByEmail(String email);

    boolean existsByPersonalIdentificationNumber(String personalIdentificationNumber);

    List<Client> findAllByParentId(Long parentId);
}
