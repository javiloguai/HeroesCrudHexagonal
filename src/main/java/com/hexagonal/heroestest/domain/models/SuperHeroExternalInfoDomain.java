package com.hexagonal.heroestest.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Immutable class for storing heroe information from an external resource
 */

@AllArgsConstructor
@Getter
public class SuperHeroExternalInfoDomain {
    private final long id;

    private final Long superheroId;

    private final String externalInfo;

}
