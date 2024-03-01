package com.hexagonal.heroestest.application.usecases;

import com.hexagonal.heroestest.domain.enums.SuperPower;
import com.hexagonal.heroestest.domain.models.SuperPowerDomain;
import com.hexagonal.heroestest.domain.ports.in.GetSuperPowersUseCase;
import com.hexagonal.heroestest.domain.ports.out.SuperPowerRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jruizh
 */
@Component
public class GetSuperPowersUseCaseImpl implements GetSuperPowersUseCase {

    private final SuperPowerRepositoryPort superPowerRepositoryPort;

    public GetSuperPowersUseCaseImpl(final SuperPowerRepositoryPort superPowerRepositoryPort) {
        this.superPowerRepositoryPort = superPowerRepositoryPort;
    }

    @Override
    public List<SuperPowerDomain> findAllSuperPowers() {
        return superPowerRepositoryPort.findAll();
    }

    @Override
    public List<SuperPowerDomain> findAllSuperPowersBySuperPower(SuperPower power) {
        return superPowerRepositoryPort.findAllBySuperPower(power);
    }
}
