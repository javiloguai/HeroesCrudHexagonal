package com.hexagonal.heroestest.infraestructure.adapters;

import com.hexagonal.heroestest.domain.enums.SuperPower;
import com.hexagonal.heroestest.domain.models.SuperHeroDomain;
import com.hexagonal.heroestest.domain.ports.out.SuperHeroRepositoryPort;
import com.hexagonal.heroestest.infraestructure.db.entities.SuperHeroEntity;
import com.hexagonal.heroestest.infraestructure.db.mappers.SuperHeroDataBaseMapper;
import com.hexagonal.heroestest.infraestructure.db.repositories.SuperHeroJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SuperHeroJPARepositoryAdapter implements SuperHeroRepositoryPort {

    private final SuperHeroJPARepository superHeroJPARepository;

    public SuperHeroJPARepositoryAdapter(final SuperHeroJPARepository superHeroJPARepository) {
        this.superHeroJPARepository = superHeroJPARepository;
    }

    @Override
    public List<SuperHeroDomain> findAll() {
        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(superHeroJPARepository.findAll());
    }

    @Override
    public Page<SuperHeroDomain> findAll(final Pageable pageable) {
        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(superHeroJPARepository.findAll(pageable));
    }

    @Override
    public List<SuperHeroDomain> findAllByName(final String name) {
        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(superHeroJPARepository.findByNameContaining(name));
    }

    @Override
    public Page<SuperHeroDomain> findAllByName(final String name, final Pageable pageable) {
        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(
                superHeroJPARepository.findByNameContainingIgnoreCase(name, pageable));
    }

    @Override
    public Optional<SuperHeroDomain> findFirstByName(String name) {
        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(superHeroJPARepository.findFirstByNameIgnoreCase(name));
    }

    @Override
    public Optional<SuperHeroDomain> findById(final Long id) {
        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(superHeroJPARepository.findById(id));
    }

    @Override
    public List<SuperHeroDomain> findSuperHeroesById(final List<Long> heroesIds) {
        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(superHeroJPARepository.findAllById(heroesIds));
    }

    @Override
    public SuperHeroDomain save(final SuperHeroDomain superHero) {
        SuperHeroEntity entity = SuperHeroDataBaseMapper.INSTANCE.domainToEntity(superHero);
        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(superHeroJPARepository.saveAndFlush(entity));
    }

    @Override
    public Optional<SuperHeroDomain> addSuperPower(final Long id, final SuperPower power) {
        return Optional.empty();
    }

    @Override
    public void deleteById(final Long id) {
        superHeroJPARepository.deleteById(id);
        superHeroJPARepository.flush();
    }

    @Override
    public void deleteAll() {
        superHeroJPARepository.deleteAll();
        superHeroJPARepository.flush();
    }
}