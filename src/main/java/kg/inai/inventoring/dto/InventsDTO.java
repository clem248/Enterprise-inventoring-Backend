package kg.inai.inventoring.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventsDTO {
    private String name;
    private String picture;
    private String qr;
    private String category;
    private String quality;
    private String location;
    private String client;
}

