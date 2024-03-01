package com.hexagonal.heroestest.infraestructure.adapters;

import com.hexagonal.heroestest.application.ports.out.AuthUserRepositoryPort;
import com.hexagonal.heroestest.infraestructure.db.entities.AuthUserEntity;
import com.hexagonal.heroestest.infraestructure.db.repositories.AuthUserJpaRepository;
import com.hexagonal.heroestest.infraestructure.db.repositories.HeroSuperPowerJPARepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserJPARepositoryAdapter implements AuthUserRepositoryPort {

    private final AuthUserJpaRepository userJpaRepository;

    public UserJPARepositoryAdapter(final HeroSuperPowerJPARepository heroSuperPowerJPARepository,
            AuthUserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<AuthUserEntity> findByUsername(final String username) {
        return userJpaRepository.findByUsername(username);
    }

    @Override
    public AuthUserEntity save(final AuthUserEntity user) {
        return userJpaRepository.save(user);
    }
}