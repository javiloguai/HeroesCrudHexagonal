package com.hexagonal.heroestest.domain.ports.in;

import com.hexagonal.heroestest.domain.enums.SuperPower;
import com.hexagonal.heroestest.domain.models.SuperPowerDomain;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jruizh
 */
@Component
public interface GetSuperPowersUseCase {

    List<SuperPowerDomain> findAllSuperPowers();

    List<SuperPowerDomain> findAllSuperPowersBySuperPower(final SuperPower power);

}
