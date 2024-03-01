package com.hexagonal.heroestest.infraestructure.db.repositories;

import com.hexagonal.heroestest.infraestructure.db.entities.AuthUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author jruizh
 */
@Repository
public interface AuthUserJpaRepository extends JpaRepository<AuthUserEntity, Integer> {
    Optional<AuthUserEntity> findByUsername(String username);

}
