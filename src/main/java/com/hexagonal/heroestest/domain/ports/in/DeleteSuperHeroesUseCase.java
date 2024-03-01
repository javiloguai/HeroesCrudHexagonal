package com.hexagonal.heroestest.domain.ports.in;

import org.springframework.stereotype.Component;

/**
 * @author jruizh
 */
@Component
public interface DeleteSuperHeroesUseCase {

    void deleteSuperHeroById(final Long id);

    void deleteAllSuperHeroes();

}
