package com.hexagonal.heroestest.domain.ports.in;

/**
 * @author jruizh
 */

public interface DeleteSuperPowerUseCase {

    void deleteAllBySuperheroId(final Long idHero);

    void deleteAll();
}
