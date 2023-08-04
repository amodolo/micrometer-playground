package dev.me.service2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "BEAN")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Bean {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    public void setName(String name) {
        this.name = name;
    }
}
