package com.hexagonal.heroestest.application.services.impl;

import com.hexagonal.heroestest.application.exception.AlreadyExistException;
import com.hexagonal.heroestest.application.exception.BusinessRuleViolatedException;
import com.hexagonal.heroestest.application.exception.NotFoundException;
import com.hexagonal.heroestest.application.mappers.SuperHeroDomainMapper;
import com.hexagonal.heroestest.application.mappers.SuperPowerDomainMapper;
import com.hexagonal.heroestest.application.models.SuperHeroDTO;
import com.hexagonal.heroestest.application.services.SuperHeroesApplicationService;
import com.hexagonal.heroestest.application.utils.ParamUtils;
import com.hexagonal.heroestest.domain.enums.SuperPower;
import com.hexagonal.heroestest.domain.models.SuperHeroDomain;
import com.hexagonal.heroestest.domain.models.SuperPowerDomain;
import com.hexagonal.heroestest.domain.ports.in.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author jruizh
 * I put some examples of other kind of call I will not implement anything on other examples
 */
@Service
@Validated
@Transactional
public class SuperHeroesApplicationServiceImpl implements SuperHeroesApplicationService {

    private static final String ID_MANDATORY = "Id field is Mandatory";

    private static final String NAME_EMPTY = "Hero name cannot be empty";

    private static final String POWERS_EMPTY = "Hero superpowers list cannot be empty";

    private static final String PAGE_MANDATORY = "page info is Mandatory";

    private static final String NAME_MANDATORY = "name field is Mandatory";

    private static final String POWER_MANDATORY = "power field is Mandatory";

    private static final String HERO_MANDATORY = "The hero Object is Mandatory";

    private final GetSuperHeroesUseCase getSuperHeroesUseCase;

    private final GetSuperPowersUseCase getSuperPowersUseCase;

    private final CreateSuperHeroUseCase createSuperHeroUseCase;

    private final CreateSuperPowerUseCase createSuperPowerUseCase;

    private final UpdateSuperHeroeUseCase updateSuperHeroeUseCase;

    private final DeleteSuperHeroesUseCase deleteSuperHeroesUseCase;

    private final DeleteSuperPowerUseCase deleteSuperPowerUseCase;

    public SuperHeroesApplicationServiceImpl(final GetSuperHeroesUseCase getSuperHeroesUseCase,
            final GetSuperPowersUseCase getSuperPowersUseCase, final CreateSuperHeroUseCase createSuperHeroeUseCase,
            final CreateSuperPowerUseCase createSuperPowerUseCase,
            final UpdateSuperHeroeUseCase updateSuperHeroeUseCase,
            final DeleteSuperHeroesUseCase deleteSuperHeroesUseCase,
            final DeleteSuperPowerUseCase deleteSuperPowerUseCase) {
        this.getSuperHeroesUseCase = getSuperHeroesUseCase;
        this.getSuperPowersUseCase = getSuperPowersUseCase;
        this.createSuperHeroUseCase = createSuperHeroeUseCase;
        this.createSuperPowerUseCase = createSuperPowerUseCase;
        this.updateSuperHeroeUseCase = updateSuperHeroeUseCase;
        this.deleteSuperHeroesUseCase = deleteSuperHeroesUseCase;
        this.deleteSuperPowerUseCase = deleteSuperPowerUseCase;
    }

    @Override
    @Cacheable(cacheNames = "allheroes")
    public List<SuperHeroDomain> getAllSuperHeroes() {
        return getSuperHeroesUseCase.getAllSuperHeroes();
    }

    @Override
    @Cacheable(cacheNames = "pagedallheroes")
    public Page<SuperHeroDomain> pageAllSuperHeroes(final Pageable pageable) {
        if (Objects.isNull(pageable)) {
            throw new BusinessRuleViolatedException(PAGE_MANDATORY);
        }

        return getSuperHeroesUseCase.pageAllSuperHeroes(pageable);
    }

    @Override
    @Cacheable(cacheNames = "heroes", key = "#name")
    public List<SuperHeroDomain> getAllSuperHeroesByName(final String name) {
        if (ParamUtils.paramNotInformed(name)) {
            throw new BusinessRuleViolatedException(NAME_MANDATORY);
        }

        return getSuperHeroesUseCase.getAllSuperHeroesByName(name);
    }

    @Override
    @Cacheable(cacheNames = "pagedheroes", key = "#name")
    public Page<SuperHeroDomain> pageAllSuperHeroesByName(final String name, final Pageable pageable) {
        if (ParamUtils.paramNotInformed(name)) {
            throw new BusinessRuleViolatedException(NAME_MANDATORY);
        }
        if (Objects.isNull(pageable)) {
            throw new BusinessRuleViolatedException(PAGE_MANDATORY);
        }

        return getSuperHeroesUseCase.pageAllSuperHeroesByName(name, pageable);
    }

    @Override
    @Cacheable(cacheNames = "powers", key = "#power")
    public List<SuperHeroDomain> getAllSuperHeroesBySuperPower(final SuperPower power) {
        if (power == null) {
            throw new BusinessRuleViolatedException(POWER_MANDATORY);

        }
        List<SuperPowerDomain> powerList = getSuperPowersUseCase.findAllSuperPowersBySuperPower(power);
        List<Long> heroesIds = powerList.stream().map(p -> p.getSuperheroId()).distinct().toList();

        return getSuperHeroesUseCase.findSuperHeroesById(heroesIds);
    }

    @Override
    @Cacheable(cacheNames = "hero", key = "#id")
    public SuperHeroDomain findSuperHeroById(final Long id) {
        if (id == null) {
            throw new BusinessRuleViolatedException(ID_MANDATORY);
        }
        return this.getHeroById(id);
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "hero", allEntries = true), @CacheEvict(value = "heroes", allEntries = true),
            @CacheEvict(value = "pagedheroes", allEntries = true), @CacheEvict(value = "allheroes", allEntries = true),
            @CacheEvict(value = "pagedallheroes", allEntries = true),
            @CacheEvict(value = "powers", allEntries = true) })
    public SuperHeroDomain createSuperHero(final SuperHeroDTO superHeroDTO) {

        this.validateHeroData(superHeroDTO);
        this.checkIfHeroAlreadyExists(superHeroDTO.getName());
        SuperHeroDomain heroDO = SuperHeroDomainMapper.INSTANCE.dtoToDomain(superHeroDTO);

        heroDO = createSuperHeroUseCase.createSuperHero(heroDO);

        final Long heroId = heroDO.getId();

        List<SuperPowerDomain> superPowers = heroDO.getSuperPower();

        superPowers.forEach(h -> h.setSuperheroId(heroId));

        createSuperPowerUseCase.assignAllSuperPowers(superPowers);

        return this.getHeroById(heroId);
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "hero", allEntries = true), @CacheEvict(value = "heroes", allEntries = true),
            @CacheEvict(value = "pagedheroes", allEntries = true), @CacheEvict(value = "allheroes", allEntries = true),
            @CacheEvict(value = "pagedallheroes", allEntries = true),
            @CacheEvict(value = "powers", allEntries = true) })
    public SuperHeroDomain updateSuperHero(final Long id, final SuperHeroDTO superHeroDTO) {

        if (id == null) {
            throw new BusinessRuleViolatedException(ID_MANDATORY);
        }
        this.validateHeroData(superHeroDTO);
        this.checkIfHeroAlreadyExists(id, superHeroDTO.getName());
        SuperHeroDomain heroDo = this.getHeroById(id);

        heroDo.setName(superHeroDTO.getName());
        heroDo.setDescription(superHeroDTO.getDescription());

        heroDo.setSuperPower(new ArrayList<>());

        updateSuperHeroeUseCase.updateSuperHero(heroDo);

        List<SuperPowerDomain> superPowers = new ArrayList<>();
        superPowers.addAll(SuperPowerDomainMapper.INSTANCE.dtoToDomain(superHeroDTO.getSuperPower()));

        deleteSuperPowerUseCase.deleteAllBySuperheroId(id);

        superPowers.forEach(h -> h.setSuperheroId(id));

        createSuperPowerUseCase.assignAllSuperPowers(superPowers);

        return this.getHeroById(id);
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "hero", allEntries = true), @CacheEvict(value = "heroes", allEntries = true),
            @CacheEvict(value = "pagedheroes", allEntries = true), @CacheEvict(value = "allheroes", allEntries = true),
            @CacheEvict(value = "pagedallheroes", allEntries = true),
            @CacheEvict(value = "powers", allEntries = true) })
    public SuperHeroDomain addSuperPower(final Long id, final SuperPower power) {

        validateAddPower(id, power);

        SuperPowerDomain powerToAdd = SuperPowerDomain.builder().superheroId(id).superPower(power).build();

        createSuperPowerUseCase.addSuperPower(powerToAdd);

        return this.getHeroById(id);
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "hero", allEntries = true), @CacheEvict(value = "heroes", allEntries = true),
            @CacheEvict(value = "pagedheroes", allEntries = true), @CacheEvict(value = "allheroes", allEntries = true),
            @CacheEvict(value = "pagedallheroes", allEntries = true),
            @CacheEvict(value = "powers", allEntries = true) })
    public void deleteSuperHeroById(final Long id) {
        if (id == null) {
            throw new BusinessRuleViolatedException(ID_MANDATORY);
        }
        this.getHeroById(id);

        deleteSuperPowerUseCase.deleteAllBySuperheroId(id);

        deleteSuperHeroesUseCase.deleteSuperHeroById(id);

    }

    @Override
    @Caching(evict = { @CacheEvict(value = "hero", allEntries = true), @CacheEvict(value = "heroes", allEntries = true),
            @CacheEvict(value = "pagedheroes", allEntries = true), @CacheEvict(value = "allheroes", allEntries = true),
            @CacheEvict(value = "pagedallheroes", allEntries = true),
            @CacheEvict(value = "powers", allEntries = true) })
    public void deleteAllSuperHeroes() {
        if (!getSuperPowersUseCase.findAllSuperPowers().isEmpty()) {
            deleteSuperPowerUseCase.deleteAll();
        }
        if (!getSuperHeroesUseCase.getAllSuperHeroes().isEmpty()) {
            deleteSuperHeroesUseCase.deleteAllSuperHeroes();
        }

    }

    private void validateAddPower(final Long id, final SuperPower power) {

        if (id == null) {
            throw new BusinessRuleViolatedException(ID_MANDATORY);
        }
        if (power == null) {
            throw new BusinessRuleViolatedException(POWER_MANDATORY);
        }

        SuperHeroDomain hiro = this.getHeroById(id);

        List<SuperPowerDomain> powerList = hiro.getSuperPower();
        List<SuperPower> hiroPowers = powerList.stream().map(p -> p.getSuperPower()).distinct().toList();

        if (!hiroPowers.isEmpty() && hiroPowers.contains(power)) {
            throw new AlreadyExistException("This Hero already owns that superpower: " + power);
        }
    }

    private SuperHeroDomain getHeroById(final Long id) {
        return getSuperHeroesUseCase.findSuperHeroById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Not found hero with id %s", id)));
    }

    private void validateHeroData(final SuperHeroDTO superHeroDTO) {
        if (superHeroDTO == null) {
            throw new BusinessRuleViolatedException(HERO_MANDATORY);
        }
        if (ParamUtils.paramNotInformed(superHeroDTO.getName())) {
            throw new BusinessRuleViolatedException(NAME_EMPTY);
        }
        if (ParamUtils.paramNotInformed(superHeroDTO.getSuperPower())) {
            throw new BusinessRuleViolatedException(POWERS_EMPTY);
        }
    }

    private void checkIfHeroAlreadyExists(final String heroName) {
        getSuperHeroesUseCase.findFirstByName(heroName).ifPresent(this::throwAlreadyExistException);
    }

    private void checkIfHeroAlreadyExists(final Long id, final String heroName) {
        getSuperHeroesUseCase.findFirstByName(heroName).ifPresent(h -> {
            if (h.getId() != id.longValue()) {
                throwAlreadyExistException(h);
            }
        });
    }

    private void throwAlreadyExistException(final SuperHeroDomain superHeroDomain) {
        throw new AlreadyExistException("This Hero already exists: " + superHeroDomain.toString());
    }

}
