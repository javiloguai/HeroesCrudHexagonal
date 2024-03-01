package com.hexagonal.heroestest.domain.ports.in;

import com.hexagonal.heroestest.domain.models.SuperHeroDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author jruizh
 */
@Component
public interface GetSuperHeroesUseCase {

    List<SuperHeroDomain> getAllSuperHeroes();

    Page<SuperHeroDomain> pageAllSuperHeroes(final Pageable pageable);

    List<SuperHeroDomain> getAllSuperHeroesByName(final String name);

    Optional<SuperHeroDomain> findFirstByName(final String name);

    Page<SuperHeroDomain> pageAllSuperHeroesByName(final String name, final Pageable pageable);

    Optional<SuperHeroDomain> findSuperHeroById(final Long id);

    List<SuperHeroDomain> findSuperHeroesById(final List<Long> heroesIds);

}
