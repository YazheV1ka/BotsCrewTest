package models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity

public class Lector {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "lectors")
    private Set<Department> departments = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Degree degree;

    // Getters and setters
}
