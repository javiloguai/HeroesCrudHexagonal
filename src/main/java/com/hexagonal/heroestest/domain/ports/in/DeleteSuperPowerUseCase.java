package com.hexagonal.heroestest.domain.ports.in;

import org.springframework.stereotype.Component;

/**
 * @author jruizh
 */
@Component
public interface DeleteSuperPowerUseCase {

    void deleteAllBySuperheroId(final Long idHero);

    void deleteAll();
}
