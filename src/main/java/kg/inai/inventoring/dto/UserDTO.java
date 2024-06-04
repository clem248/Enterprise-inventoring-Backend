package kg.inai.inventoring.dto;




import kg.inai.inventoring.entity.Role;

import java.util.Set;

public record UserDTO(
        Long id,
        String email,
        String username,
        String password,
        Set<Role> role
) {
}
