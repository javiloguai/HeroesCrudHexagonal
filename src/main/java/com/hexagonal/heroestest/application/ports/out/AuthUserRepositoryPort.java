package com.hexagonal.heroestest.application.ports.out;

import com.hexagonal.heroestest.infraestructure.db.entities.AuthUserEntity;

import java.util.Optional;

public interface AuthUserRepositoryPort {

    Optional<AuthUserEntity> findByUsername(final String username);

    AuthUserEntity save(final AuthUserEntity user);
}
