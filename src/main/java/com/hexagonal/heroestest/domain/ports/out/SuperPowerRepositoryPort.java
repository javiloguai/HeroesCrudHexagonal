package com.hexagonal.heroestest.domain.ports.out;

import com.hexagonal.heroestest.domain.enums.SuperPower;
import com.hexagonal.heroestest.domain.models.SuperPowerDomain;

import java.util.List;

public interface SuperPowerRepositoryPort {

    List<SuperPowerDomain> findAll();

    List<SuperPowerDomain> findAllBySuperPower(final SuperPower power);

    SuperPowerDomain save(final SuperPowerDomain superPower);

    void saveAll(final List<SuperPowerDomain> superPowers);

    void deleteAll();

    void deleteAllBySuperheroId(final Long heroId);

}
