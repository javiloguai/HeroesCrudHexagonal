package com.hexagonal.heroestest.domain.ports.in;

import com.hexagonal.heroestest.domain.models.SuperPowerDomain;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jruizh
 */
@Component
public interface CreateSuperPowerUseCase {

    void assignAllSuperPowers(final List<SuperPowerDomain> superPowers);

    SuperPowerDomain addSuperPower(final SuperPowerDomain power);
}
