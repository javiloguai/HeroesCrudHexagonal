package com.hexagonal.heroestest.domain.ports.in;

import com.hexagonal.heroestest.domain.enums.SuperPower;
import com.hexagonal.heroestest.domain.models.SuperHeroDomain;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author jruizh
 */
@Component
public interface UpdateSuperHeroeUseCase {
    Optional<SuperHeroDomain> addSuperPower(final Long id, final SuperPower power);

    SuperHeroDomain updateSuperHero(final SuperHeroDomain superHero);

}
