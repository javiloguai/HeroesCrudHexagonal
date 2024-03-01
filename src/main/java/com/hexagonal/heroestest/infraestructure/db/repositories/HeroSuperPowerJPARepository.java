package com.hexagonal.heroestest.infraestructure.db.repositories;

import com.hexagonal.heroestest.domain.enums.SuperPower;
import com.hexagonal.heroestest.infraestructure.db.entities.HeroSuperPowerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroSuperPowerJPARepository extends JpaRepository<HeroSuperPowerEntity, Long> {

    List<HeroSuperPowerEntity> findAllBySuperheroId(final Long superheroId);

    void deleteAllBySuperheroId(final Long superheroId);

    List<HeroSuperPowerEntity> findBySuperPower(final SuperPower power);

}