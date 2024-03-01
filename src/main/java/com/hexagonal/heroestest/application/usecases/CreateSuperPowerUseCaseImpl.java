package com.hexagonal.heroestest.application.usecases;

import com.hexagonal.heroestest.domain.models.SuperPowerDomain;
import com.hexagonal.heroestest.domain.ports.in.CreateSuperPowerUseCase;
import com.hexagonal.heroestest.domain.ports.out.SuperPowerRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jruizh
 */
@Component
public class CreateSuperPowerUseCaseImpl implements CreateSuperPowerUseCase {

    private final SuperPowerRepositoryPort superPowerRepositoryPort;

    public CreateSuperPowerUseCaseImpl(final SuperPowerRepositoryPort superPowerRepositoryPort) {
        this.superPowerRepositoryPort = superPowerRepositoryPort;
    }

    @Override
    public void assignAllSuperPowers(final List<SuperPowerDomain> superPowers) {
        superPowerRepositoryPort.saveAll(superPowers);
    }

    @Override
    public SuperPowerDomain addSuperPower(SuperPowerDomain power) {
        return superPowerRepositoryPort.save(power);
    }
}
