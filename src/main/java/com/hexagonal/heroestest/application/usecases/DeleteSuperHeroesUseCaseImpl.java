package com.hexagonal.heroestest.application.usecases;

import com.hexagonal.heroestest.domain.ports.in.DeleteSuperHeroesUseCase;
import com.hexagonal.heroestest.domain.ports.out.SuperHeroRepositoryPort;
import org.springframework.stereotype.Component;

/**
 * @author jruizh
 */
@Component
public class DeleteSuperHeroesUseCaseImpl implements DeleteSuperHeroesUseCase {

    private final SuperHeroRepositoryPort superHeroRepositoryPort;

    public DeleteSuperHeroesUseCaseImpl(final SuperHeroRepositoryPort superHeroRepositoryPort) {
        this.superHeroRepositoryPort = superHeroRepositoryPort;
    }

    @Override
    public void deleteSuperHeroById(final Long id) {
        superHeroRepositoryPort.deleteById(id);
    }

    @Override
    public void deleteAllSuperHeroes() {
        superHeroRepositoryPort.deleteAll();

    }

}
