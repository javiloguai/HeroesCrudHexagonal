package com.hexagonal.heroestest.infraestructure.db.entities;

import com.hexagonal.heroestest.domain.enums.SuperPower;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "HERO_SUPER_POWER")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeroSuperPowerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "ID")
    private long id;

    @Column(name = "SUPERHERO_ID", nullable = false)
    private Long superheroId;

    @Enumerated(EnumType.STRING)
    @Column(name = "SUPER_POWER", nullable = false)
    private SuperPower superPower;

}
