package com.hexagonal.heroestest.domain.ports.in;

import com.hexagonal.heroestest.domain.models.SuperHeroDomain;

/**
 * @author jruizh
 */
public interface CreateSuperHeroUseCase {

    SuperHeroDomain createSuperHero(final SuperHeroDomain superHero);

}
