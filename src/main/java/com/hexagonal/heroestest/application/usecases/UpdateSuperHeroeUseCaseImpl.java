package com.hexagonal.heroestest.application.usecases;

import com.hexagonal.heroestest.domain.enums.SuperPower;
import com.hexagonal.heroestest.domain.models.SuperHeroDomain;
import com.hexagonal.heroestest.domain.ports.in.UpdateSuperHeroeUseCase;
import com.hexagonal.heroestest.domain.ports.out.SuperHeroRepositoryPort;

import java.util.Optional;

/**
 * @author jruizh
 */
public class UpdateSuperHeroeUseCaseImpl implements UpdateSuperHeroeUseCase {

    private final SuperHeroRepositoryPort superHeroRepositoryPort;

    public UpdateSuperHeroeUseCaseImpl(final SuperHeroRepositoryPort superHeroRepositoryPort) {
        this.superHeroRepositoryPort = superHeroRepositoryPort;
    }

    @Override
    public Optional<SuperHeroDomain> addSuperPower(final Long id, final SuperPower power) {
        return superHeroRepositoryPort.addSuperPower(id, power);
    }

    @Override
    public SuperHeroDomain updateSuperHero(SuperHeroDomain superHero) {
        return superHeroRepositoryPort.save(superHero);
    }

}
