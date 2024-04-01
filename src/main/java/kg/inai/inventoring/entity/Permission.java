package kg.inai.inventoring.entity;

import javax.persistence.*;

@Entity
@Table
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
