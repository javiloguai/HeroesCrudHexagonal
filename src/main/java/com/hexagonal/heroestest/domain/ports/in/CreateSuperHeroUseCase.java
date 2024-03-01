package com.hexagonal.heroestest.domain.ports.in;

import com.hexagonal.heroestest.domain.models.SuperHeroDomain;
import org.springframework.stereotype.Component;

/**
 * @author jruizh
 */
@Component
public interface CreateSuperHeroUseCase {

    SuperHeroDomain createSuperHero(final SuperHeroDomain superHero);

}
