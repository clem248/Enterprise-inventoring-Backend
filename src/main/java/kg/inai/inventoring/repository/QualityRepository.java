package kg.inai.inventoring.repository;

import kg.inai.inventoring.entity.Quality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QualityRepository extends JpaRepository<Quality, Long> {
    Optional<Quality> findByQualityName(String qualityName);
    List<Quality> findAll();

}
