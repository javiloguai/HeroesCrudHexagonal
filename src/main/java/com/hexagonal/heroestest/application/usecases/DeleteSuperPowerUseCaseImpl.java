package com.hexagonal.heroestest.application.usecases;

import com.hexagonal.heroestest.domain.ports.in.DeleteSuperPowerUseCase;
import com.hexagonal.heroestest.domain.ports.out.SuperPowerRepositoryPort;
import org.springframework.stereotype.Component;

/**
 * @author jruizh
 */
@Component
public class DeleteSuperPowerUseCaseImpl implements DeleteSuperPowerUseCase {

    private final SuperPowerRepositoryPort superPowerRepositoryPort;

    public DeleteSuperPowerUseCaseImpl(final SuperPowerRepositoryPort superPowerRepositoryPort) {
        this.superPowerRepositoryPort = superPowerRepositoryPort;
    }

    @Override
    public void deleteAllBySuperheroId(final Long heroId) {
        superPowerRepositoryPort.deleteAllBySuperheroId(heroId);
    }

    @Override
    public void deleteAll() {
        superPowerRepositoryPort.deleteAll();
    }
}
