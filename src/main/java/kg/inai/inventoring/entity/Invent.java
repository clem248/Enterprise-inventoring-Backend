package kg.inai.inventoring.entity;

import javax.persistence.*;

@Entity
@Table
public class Invent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
