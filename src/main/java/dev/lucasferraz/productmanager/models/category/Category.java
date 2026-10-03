package dev.lucasferraz.productmanager.models.category;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // categoria deve ser unica
    @Column(nullable = false, unique = true)
    private String name;
    private String description;
    private boolean active;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    public Category(String name, String description, boolean active) {
        this.name = name;
        this.description = description;
        this.active = active;
    }

    public void updateDetails(String name, String description, Boolean active) {
        if (name != null && !name.isBlank())
            this.name = name;
        if (description != null)
            this.description = description;
        if (active != null)
            this.active = active;
    }
}
