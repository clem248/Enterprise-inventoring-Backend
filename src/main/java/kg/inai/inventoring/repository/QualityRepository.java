package kg.inai.inventoring.repository;

import kg.inai.inventoring.entity.Quality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualityRepository extends JpaRepository<Quality, Long> {
}
