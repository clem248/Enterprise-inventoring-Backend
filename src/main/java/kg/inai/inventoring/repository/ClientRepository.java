package kg.inai.inventoring.repository;

import kg.inai.inventoring.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByLogin(String login);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Client getClientByLogin(String login);

    boolean existsByLogin(String login);

    @Query("SELECT c FROM Client c WHERE LOWER(c.fullName) LIKE CONCAT(LOWER(:fullNamePrefix), '%') " +
            "AND LOWER(c.login) LIKE CONCAT(LOWER(:loginPrefix), '%') ")
    Page<Client> findByFilters(String fullNamePrefix, String loginPrefix, Pageable pageable);

}
