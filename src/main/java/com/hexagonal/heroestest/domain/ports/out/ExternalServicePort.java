package com.hexagonal.heroestest.domain.ports.out;

import com.hexagonal.heroestest.domain.models.SuperHeroExternalInfoDomain;

public interface ExternalServicePort {

    SuperHeroExternalInfoDomain getSuperHeroExternalInfo(final long superheroId);
}
