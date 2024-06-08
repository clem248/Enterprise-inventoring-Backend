package kg.inai.inventoring.repository;

import jdk.jfr.Category;
import kg.inai.inventoring.entity.Location;
import org.hibernate.cfg.JPAIndexHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByLocationName(String locationName);
    List<Location> findAll();

}
