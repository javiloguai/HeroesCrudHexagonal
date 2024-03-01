package com.hexagonal.heroestest.domain.ports.in;

import com.hexagonal.heroestest.domain.models.SuperPowerDomain;

import java.util.List;

/**
 * @author jruizh
 */

public interface CreateSuperPowerUseCase {

    void assignAllSuperPowers(final List<SuperPowerDomain> superPowers);

    SuperPowerDomain addSuperPower(final SuperPowerDomain power);
}
