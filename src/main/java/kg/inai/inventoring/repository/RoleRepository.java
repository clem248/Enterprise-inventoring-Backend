package kg.inai.inventoring.repository;

import kg.inai.inventoring.entity.ERole;
import kg.inai.inventoring.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

}
