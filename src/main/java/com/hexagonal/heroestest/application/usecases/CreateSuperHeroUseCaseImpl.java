package com.hexagonal.heroestest.application.usecases;

import com.hexagonal.heroestest.domain.models.SuperHeroDomain;
import com.hexagonal.heroestest.domain.ports.in.CreateSuperHeroUseCase;
import com.hexagonal.heroestest.domain.ports.out.SuperHeroRepositoryPort;

/**
 * @author jruizh
 */
public class CreateSuperHeroUseCaseImpl implements CreateSuperHeroUseCase {

    private final SuperHeroRepositoryPort superHeroRepositoryPort;

    public CreateSuperHeroUseCaseImpl(final SuperHeroRepositoryPort superHeroRepositoryPort) {
        this.superHeroRepositoryPort = superHeroRepositoryPort;
    }

    @Override
    public SuperHeroDomain createSuperHero(final SuperHeroDomain superHero) {
        return superHeroRepositoryPort.save(superHero);
    }

}
