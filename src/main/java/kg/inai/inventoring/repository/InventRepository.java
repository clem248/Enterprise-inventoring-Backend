package kg.inai.inventoring.repository;

import kg.inai.inventoring.entity.Invents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventRepository extends JpaRepository<Invents, Long> {
}
