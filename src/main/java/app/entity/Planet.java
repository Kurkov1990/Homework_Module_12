package app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "planet")

public class Planet {
    @Id
    @Pattern(regexp = "^[A-Z0-9]+$")
    @Column(length = 50, nullable = false)
    private String id;

    @Size(min = 1, max = 500)
    @Column(length = 500, nullable = false)
    private String name;
}
