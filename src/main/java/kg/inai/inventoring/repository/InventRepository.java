package kg.inai.inventoring.repository;

import kg.inai.inventoring.entity.Invents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface InventRepository extends JpaRepository<Invents, Long> {
    @Query("SELECT i FROM Invents i WHERE " +
            "(:category IS NULL OR i.category = :category) AND " +
            "(:location IS NULL OR i.location = :location) AND " +
            "(:client IS NULL OR i.client = :client)")
    Page<Invents> findByFilters(
            @Param("category") String category,
            @Param("location") String location,
            @Param("client") String client,
            Pageable pageable);

    Optional<Invents> findByQr(String qr);


}
