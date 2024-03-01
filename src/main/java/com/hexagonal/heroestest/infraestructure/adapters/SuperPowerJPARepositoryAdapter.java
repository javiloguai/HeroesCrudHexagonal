package com.hexagonal.heroestest.infraestructure.adapters;

import com.hexagonal.heroestest.domain.enums.SuperPower;
import com.hexagonal.heroestest.domain.models.SuperPowerDomain;
import com.hexagonal.heroestest.domain.ports.out.SuperPowerRepositoryPort;
import com.hexagonal.heroestest.infraestructure.db.entities.HeroSuperPowerEntity;
import com.hexagonal.heroestest.infraestructure.db.mappers.SuperPowerDataBaseMapper;
import com.hexagonal.heroestest.infraestructure.db.repositories.HeroSuperPowerJPARepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SuperPowerJPARepositoryAdapter implements SuperPowerRepositoryPort {

    private final HeroSuperPowerJPARepository heroSuperPowerJPARepository;

    public SuperPowerJPARepositoryAdapter(final HeroSuperPowerJPARepository heroSuperPowerJPARepository) {
        this.heroSuperPowerJPARepository = heroSuperPowerJPARepository;
    }

    @Override
    public List<SuperPowerDomain> findAll() {
        return SuperPowerDataBaseMapper.INSTANCE.entityToDomain(heroSuperPowerJPARepository.findAll());
    }

    @Override
    public List<SuperPowerDomain> findAllBySuperPower(final SuperPower power) {

        return SuperPowerDataBaseMapper.INSTANCE.entityToDomain(heroSuperPowerJPARepository.findBySuperPower(power));
    }

    @Override
    public SuperPowerDomain save(final SuperPowerDomain superPower) {
        HeroSuperPowerEntity entity = SuperPowerDataBaseMapper.INSTANCE.domainToEntity(superPower);
        return SuperPowerDataBaseMapper.INSTANCE.entityToDomain(heroSuperPowerJPARepository.saveAndFlush(entity));
    }

    @Override
    public void saveAll(final List<SuperPowerDomain> superPowers) {
        heroSuperPowerJPARepository.saveAllAndFlush(SuperPowerDataBaseMapper.INSTANCE.domainToEntity(superPowers));
    }

    @Override
    public void deleteAll() {
        heroSuperPowerJPARepository.deleteAll();
        heroSuperPowerJPARepository.flush();
    }

    @Override
    public void deleteAllBySuperheroId(final Long heroId) {
        heroSuperPowerJPARepository.deleteAllBySuperheroId(heroId);
        heroSuperPowerJPARepository.flush();
    }
}