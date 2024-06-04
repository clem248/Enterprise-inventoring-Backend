package kg.inai.inventoring.repository;

import kg.inai.inventoring.entity.Invents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface InventRepository extends JpaRepository<Invents, Long> {
//    @Query("SELECT p FROM Invents p " +
//            "JOIN p.category a " +
//            "JOIN p.quality s " +
//            "JOIN p.location c " +
//            "AND LOWER(s.name) LIKE CONCAT(LOWER(:serviceName), '%') " +
//            "AND LOWER(c.fullName) LIKE CONCAT(LOWER(:clientFullName), '%') " +
//            "AND LOWER(p.paymentStatus) LIKE CONCAT(LOWER(:paymentStatusPrefix), '%') " +
//            "AND p.createdAt >= :startDate " +
//            "AND p.createdAt <= :endDate " +
//            "AND p.paidAt >= :startDatePaid " +
//            "AND p.paidAt <= :endDatePaid ")
//    Page<Invents> findByFilters(String requisitePrefix,
//                                BigDecimal minAmount,
//                                BigDecimal maxAmount,
//                                String serviceName,
//                                String clientFullName,
//                                String paymentStatusPrefix,
//                                LocalDateTime startDate,
//                                LocalDateTime endDate,
//                                LocalDateTime startDatePaid,
//                                LocalDateTime endDatePaid,
//                                Pageable pageable);

    Optional<Invents> findByQr(String qr);


}
