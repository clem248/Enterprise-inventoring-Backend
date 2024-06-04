package kg.inai.inventoring.dto;

import java.math.BigDecimal;

public record ClientDTO(
        Long id,
        String fullName,
        String login,
        String password,
        String ipAddresses
) {
}
