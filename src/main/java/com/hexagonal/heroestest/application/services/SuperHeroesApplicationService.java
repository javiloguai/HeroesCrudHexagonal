package com.hexagonal.heroestest.application.services;

import com.hexagonal.heroestest.application.models.SuperHeroDTO;
import com.hexagonal.heroestest.domain.enums.SuperPower;
import com.hexagonal.heroestest.domain.models.SuperHeroDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author jruizh
 * I put some examples of other kind of call I will not implement anything on other examples
 */

public interface SuperHeroesApplicationService {

    List<SuperHeroDomain> getAllSuperHeroes();

    Page<SuperHeroDomain> pageAllSuperHeroes(final Pageable pageable);

    List<SuperHeroDomain> getAllSuperHeroesByName(final String name);

    Page<SuperHeroDomain> pageAllSuperHeroesByName(final String name, final Pageable pageable);

    List<SuperHeroDomain> getAllSuperHeroesBySuperPower(final SuperPower power);

    SuperHeroDomain findSuperHeroById(final Long id);

    SuperHeroDomain createSuperHero(final SuperHeroDTO superHeroDTO);

    SuperHeroDomain updateSuperHero(final Long id, final SuperHeroDTO superHeroDTO);

    SuperHeroDomain addSuperPower(final Long id, final SuperPower power);

    void deleteSuperHeroById(final Long id);

    void deleteAllSuperHeroes();

}
