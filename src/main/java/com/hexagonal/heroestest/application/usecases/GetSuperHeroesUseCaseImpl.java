package com.hexagonal.heroestest.application.usecases;

import com.hexagonal.heroestest.domain.models.SuperHeroDomain;
import com.hexagonal.heroestest.domain.ports.in.GetSuperHeroesUseCase;
import com.hexagonal.heroestest.domain.ports.out.SuperHeroRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author jruizh
 */
public class GetSuperHeroesUseCaseImpl implements GetSuperHeroesUseCase {

    private final SuperHeroRepositoryPort superHeroRepositoryPort;

    public GetSuperHeroesUseCaseImpl(final SuperHeroRepositoryPort superHeroRepositoryPort) {
        this.superHeroRepositoryPort = superHeroRepositoryPort;
    }

    @Override
    public List<SuperHeroDomain> getAllSuperHeroes() {
        return superHeroRepositoryPort.findAll();
    }

    @Override
    public Page<SuperHeroDomain> pageAllSuperHeroes(final Pageable pageable) {
        return superHeroRepositoryPort.findAll(pageable);
    }

    @Override
    public List<SuperHeroDomain> getAllSuperHeroesByName(final String name) {
        return superHeroRepositoryPort.findAllByName(name);
    }

    @Override
    public Optional<SuperHeroDomain> findFirstByName(String name) {
        return superHeroRepositoryPort.findFirstByName(name);
    }

    @Override
    public Page<SuperHeroDomain> pageAllSuperHeroesByName(final String name, final Pageable pageable) {
        return superHeroRepositoryPort.findAllByName(name, pageable);

    }

    @Override
    public Optional<SuperHeroDomain> findSuperHeroById(final Long id) {
        return superHeroRepositoryPort.findById(id);
    }

    @Override
    public List<SuperHeroDomain> findSuperHeroesById(List<Long> heroesIds) {
        return superHeroRepositoryPort.findSuperHeroesById(heroesIds);
    }

}
