package kg.inai.inventoring.repository;

import jdk.jfr.Category;
import kg.inai.inventoring.entity.Location;
import org.hibernate.cfg.JPAIndexHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
