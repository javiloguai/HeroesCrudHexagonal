package com.hexagonal.heroestest.domain.ports.out;

import com.hexagonal.heroestest.domain.enums.SuperPower;
import com.hexagonal.heroestest.domain.models.SuperHeroDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SuperHeroRepositoryPort {
    List<SuperHeroDomain> findAll();

    Page<SuperHeroDomain> findAll(final Pageable pageable);

    List<SuperHeroDomain> findAllByName(final String name);

    Page<SuperHeroDomain> findAllByName(final String name, final Pageable pageable);

    Optional<SuperHeroDomain> findFirstByName(String name);
    
    Optional<SuperHeroDomain> findById(final Long id);

    List<SuperHeroDomain> findSuperHeroesById(final List<Long> heroesIds);

    SuperHeroDomain save(final SuperHeroDomain superHero);

    Optional<SuperHeroDomain> addSuperPower(final Long id, final SuperPower power);

    void deleteById(final Long id);

    void deleteAll();

}
