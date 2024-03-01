package com.hexagonal.heroestest.domain.ports.in;

/**
 * @author jruizh
 */

public interface DeleteSuperHeroesUseCase {

    void deleteSuperHeroById(final Long id);

    void deleteAllSuperHeroes();

}
