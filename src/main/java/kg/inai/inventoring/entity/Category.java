package kg.inai.inventoring.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String category_name;

    public Category() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category_name;
    }

    public void setCategory(String category) {
        this.category_name = category;
    }

    public Category(Long id, String category) {
        this.id = id;
        this.category_name = category;
    }
}
