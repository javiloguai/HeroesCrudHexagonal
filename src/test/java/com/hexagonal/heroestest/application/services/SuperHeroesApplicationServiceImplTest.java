package com.hexagonal.heroestest.application.services;

import com.hexagonal.heroestest.application.exception.AlreadyExistException;
import com.hexagonal.heroestest.application.exception.BusinessRuleViolatedException;
import com.hexagonal.heroestest.application.exception.NotFoundException;
import com.hexagonal.heroestest.application.models.SuperHeroDTO;
import com.hexagonal.heroestest.application.services.impl.SuperHeroesApplicationServiceImpl;
import com.hexagonal.heroestest.domain.enums.SuperPower;
import com.hexagonal.heroestest.domain.models.SuperHeroDomain;
import com.hexagonal.heroestest.domain.models.SuperPowerDomain;
import com.hexagonal.heroestest.domain.ports.in.*;
import com.hexagonal.heroestest.factories.SuperHeroFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

/**
 * @author jruizh
 */
@ExtendWith({ SpringExtension.class, MockitoExtension.class })
@ContextConfiguration
public class SuperHeroesApplicationServiceImplTest {

    private static final String ID_MANDATORY = "Id field is Mandatory";

    private static final String NAME_EMPTY = "Hero name cannot be empty";

    private static final String POWERS_EMPTY = "Hero superpowers list cannot be empty";

    private static final String PAGE_MANDATORY = "page info is Mandatory";

    private static final String NAME_MANDATORY = "name field is Mandatory";

    private static final String POWER_MANDATORY = "power field is Mandatory";

    private static final String HERO_MANDATORY = "The hero Object is Mandatory";

    //@InjectMocks
    private SuperHeroesApplicationServiceImpl superHeroesApplicationService;

    @Mock
    private GetSuperHeroesUseCase getSuperHeroesUseCase;

    @Mock
    private GetSuperPowersUseCase getSuperPowersUseCase;

    @Mock
    private CreateSuperHeroUseCase createSuperHeroUseCase;

    @Mock
    private CreateSuperPowerUseCase createSuperPowerUseCase;

    @Mock
    private UpdateSuperHeroeUseCase updateSuperHeroeUseCase;

    @Mock
    private DeleteSuperHeroesUseCase deleteSuperHeroesUseCase;

    @Mock
    private DeleteSuperPowerUseCase deleteSuperPowerUseCase;

    @Captor
    private ArgumentCaptor<SuperPowerDomain> superPowerDomainArgumentCaptor;

    @Captor
    private ArgumentCaptor<SuperHeroDomain> superHeroDomainArgumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.superHeroesApplicationService = new SuperHeroesApplicationServiceImpl(getSuperHeroesUseCase,
                getSuperPowersUseCase, createSuperHeroUseCase, createSuperPowerUseCase, updateSuperHeroeUseCase,
                deleteSuperHeroesUseCase, deleteSuperPowerUseCase);
    }

    /**
     * Tests for getAllSuperHeroes method
     */
    @Nested
    class getAllSuperHeroesTest {

        @Test
        void givenNullPage_thenThrowException() {

            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> superHeroesApplicationService.pageAllSuperHeroes(null));
            Assertions.assertEquals(PAGE_MANDATORY, ex.getMessage());

        }

        @Test
        void givenNonExistingHeroes_thenReturnEmptyPage() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

            final List<SuperHeroDomain> hlist = List.of();

            final Pageable pageable = PageRequest.of(0, 20);
            final Page<SuperHeroDomain> pageResult = new PageImpl<>(hlist, pageable, 1);
            Mockito.when(getSuperHeroesUseCase.pageAllSuperHeroes(any(Pageable.class))).thenReturn(pageResult);

            // when
            final Page<SuperHeroDomain> pageResultDomain = superHeroesApplicationService.pageAllSuperHeroes(pageable);

            // then
            Assertions.assertNotNull(pageResultDomain);
            Assertions.assertTrue(pageResultDomain.isEmpty());
            Assertions.assertEquals(hlist.size(), pageResultDomain.getContent().size());

        }

        @Test
        void givenNonExistingHeroes_thenReturnEmptyList() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

            final List<SuperHeroDomain> hlist = List.of();

            Mockito.when(getSuperHeroesUseCase.getAllSuperHeroes()).thenReturn(hlist);

            // when
            final List<SuperHeroDomain> listResultDomain = superHeroesApplicationService.getAllSuperHeroes();

            // then
            Assertions.assertNotNull(listResultDomain);
            Assertions.assertTrue(listResultDomain.isEmpty());
            Assertions.assertEquals(hlist.size(), listResultDomain.size());
        }

        @Test
        void givenExistingHeroes_thenReturnAllPagedHeroes() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

            final SuperHeroDomain h1 = SuperHeroFactory.getDO(1L);
            final SuperHeroDomain h2 = SuperHeroFactory.getDO(2L);

            final List<SuperHeroDomain> hlist = List.of(h1, h2);

            final Pageable pageable = PageRequest.of(0, 20);
            final Page<SuperHeroDomain> pageResult = new PageImpl<>(hlist, pageable, 1);
            Mockito.when(getSuperHeroesUseCase.pageAllSuperHeroes(any(Pageable.class))).thenReturn(pageResult);

            // when
            final Page<SuperHeroDomain> pageResultDomain = superHeroesApplicationService.pageAllSuperHeroes(pageable);

            // then
            Assertions.assertEquals(hlist.size(), pageResultDomain.getTotalElements());
            Assertions.assertEquals(hlist.get(0).getId(), pageResultDomain.toList().get(0).getId());
            Assertions.assertEquals(hlist.get(1).getId(), pageResultDomain.toList().get(1).getId());
        }

        @Test
        void givenExistingHeroes_thenReturnAllHeroes() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

            final SuperHeroDomain h1 = SuperHeroFactory.getDO(1L);
            final SuperHeroDomain h2 = SuperHeroFactory.getDO(2L);

            final List<SuperHeroDomain> hlist = List.of(h1, h2);

            Mockito.when(getSuperHeroesUseCase.getAllSuperHeroes()).thenReturn(hlist);

            // when
            final List<SuperHeroDomain> listResultDomain = superHeroesApplicationService.getAllSuperHeroes();

            // then
            Assertions.assertEquals(hlist.size(), listResultDomain.size());
            Assertions.assertEquals(hlist.get(0).getId(), listResultDomain.get(0).getId());
            Assertions.assertEquals(hlist.get(1).getId(), listResultDomain.get(1).getId());
        }

    }

    /**
     * Tests for getAllSuperHeroesByName method
     */
    @Nested
    class getAllSuperHeroesByNameTest {

        @Test
        void givenNullName_thenThrowException() {

            final Pageable pageable = PageRequest.of(0, 20);

            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> superHeroesApplicationService.getAllSuperHeroesByName(null));
            Assertions.assertEquals(NAME_MANDATORY, ex.getMessage());

            final BusinessRuleViolatedException ex2 = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> superHeroesApplicationService.pageAllSuperHeroesByName(null, pageable));
            Assertions.assertEquals(NAME_MANDATORY, ex2.getMessage());

        }

        @Test
        void givenNullPage_thenThrowException() {

            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> superHeroesApplicationService.pageAllSuperHeroesByName("name", null));
            Assertions.assertEquals(PAGE_MANDATORY, ex.getMessage());

        }

        @Test
        void givenNonMatchingHeroes_thenReturnEmptyPage() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

            final List<SuperHeroDomain> hlist = List.of();

            final Pageable pageable = PageRequest.of(0, 20);
            final Page<SuperHeroDomain> pageResult = new PageImpl<>(hlist, pageable, 1);
            Mockito.when(getSuperHeroesUseCase.pageAllSuperHeroesByName(ArgumentMatchers.any(String.class),
                    ArgumentMatchers.any(Pageable.class))).thenReturn(pageResult);

            // when
            final Page<SuperHeroDomain> pageResultDomain = superHeroesApplicationService.pageAllSuperHeroesByName("XXX",
                    pageable);

            // then
            Assertions.assertNotNull(pageResultDomain);
            Assertions.assertTrue(pageResultDomain.isEmpty());
            Assertions.assertEquals(hlist.size(), pageResultDomain.getContent().size());

        }

        @Test
        void givenNonMatchingHeroes_thenReturnEmptyList() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

            final List<SuperHeroDomain> hlist = List.of();

            Mockito.when(getSuperHeroesUseCase.getAllSuperHeroesByName(ArgumentMatchers.any(String.class)))
                    .thenReturn(hlist);

            // when
            final List<SuperHeroDomain> listResultDomain = superHeroesApplicationService.getAllSuperHeroesByName("XXX");

            // then
            Assertions.assertNotNull(listResultDomain);
            Assertions.assertTrue(listResultDomain.isEmpty());
            Assertions.assertEquals(hlist.size(), listResultDomain.size());
        }

        @Test
        void givenMatchingHeroes_thenReturnAllMatchingPagedHeroes() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

            final SuperHeroDomain h1 = SuperHeroFactory.getDO(1L);
            final SuperHeroDomain h2 = SuperHeroFactory.getDO(2L);

            final List<SuperHeroDomain> hlist = List.of(h1, h2);

            final Pageable pageable = PageRequest.of(0, 20);
            final Page<SuperHeroDomain> pageResult = new PageImpl<>(hlist, pageable, 1);
            Mockito.when(getSuperHeroesUseCase.pageAllSuperHeroesByName(h1.getName(), pageable)).thenReturn(pageResult);

            // when
            final Page<SuperHeroDomain> pageResultDomain = superHeroesApplicationService.pageAllSuperHeroesByName(
                    h1.getName(), pageable);

            // then
            Assertions.assertEquals(hlist.size(), pageResultDomain.getTotalElements());
            Assertions.assertEquals(hlist.get(0).getId(), pageResultDomain.toList().get(0).getId());
            Assertions.assertEquals(hlist.get(1).getId(), pageResultDomain.toList().get(1).getId());
        }

        @Test
        void givenMatchingHeroes_thenReturnMatchingHeroes() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

            final SuperHeroDomain h1 = SuperHeroFactory.getDO(1L);
            final SuperHeroDomain h2 = SuperHeroFactory.getDO(2L);

            final List<SuperHeroDomain> hlist = List.of(h1, h2);

            Mockito.when(getSuperHeroesUseCase.getAllSuperHeroesByName(h1.getName())).thenReturn(hlist);

            // when
            final List<SuperHeroDomain> listResultDomain = superHeroesApplicationService.getAllSuperHeroesByName(
                    h1.getName());

            // then
            Assertions.assertEquals(hlist.size(), listResultDomain.size());
            Assertions.assertEquals(hlist.get(0).getId(), listResultDomain.get(0).getId());
            Assertions.assertEquals(hlist.get(1).getId(), listResultDomain.get(1).getId());
        }

    }

    /**
     * Tests for getAllSuperHeroesBySuperPower method
     */
    @Nested
    class getAllSuperHeroesBySuperPowerTest {

        @Test
        void givenNullPower_thenThrowException() {

            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> superHeroesApplicationService.getAllSuperHeroesBySuperPower(null));
            Assertions.assertEquals(POWER_MANDATORY, ex.getMessage());

        }

        @Test
        void givenNonMatchingPower_thenReturnEmptyList() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

            final List<SuperPowerDomain> plist = List.of();
            final List<SuperHeroDomain> hlist = List.of();

            List<Long> heroesIds = plist.stream().map(p -> p.getSuperheroId()).distinct().toList();

            Mockito.when(getSuperPowersUseCase.findAllSuperPowersBySuperPower(ArgumentMatchers.any(SuperPower.class)))
                    .thenReturn(plist);

            Mockito.when(getSuperHeroesUseCase.findSuperHeroesById(heroesIds)).thenReturn(hlist);

            // when
            final List<SuperHeroDomain> listResultDomain = superHeroesApplicationService.getAllSuperHeroesBySuperPower(
                    SuperPower.MOLECULAR_COMBUSTION);

            // then
            Assertions.assertNotNull(listResultDomain);
            Assertions.assertTrue(listResultDomain.isEmpty());
            Assertions.assertEquals(hlist.size(), listResultDomain.size());
        }

        @Test
        void givenMatchingPower_thenReturnMatchingHeroes() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

            final SuperHeroDomain h1 = SuperHeroFactory.getDO(1L);
            final SuperHeroDomain h2 = SuperHeroFactory.getDO(2L);

            SuperPower power = SuperHeroFactory.getPowerEntity(1L).getSuperPower();

            SuperPowerDomain p1 = SuperHeroFactory.getPowerDO(1L, SuperHeroFactory.POWER_ID);
            SuperPowerDomain p2 = SuperHeroFactory.getPowerDO(2L, SuperHeroFactory.POWER_ID + 1);

            final List<SuperPowerDomain> plist = List.of(p1, p2);
            final List<SuperHeroDomain> hlist = List.of(h1, h2);

            List<Long> heroesIds = plist.stream().map(p -> p.getSuperheroId()).distinct().toList();

            Mockito.when(getSuperPowersUseCase.findAllSuperPowersBySuperPower(power)).thenReturn(plist);

            Mockito.when(getSuperHeroesUseCase.findSuperHeroesById(heroesIds)).thenReturn(hlist);

            // when
            final List<SuperHeroDomain> listResultDomain = superHeroesApplicationService.getAllSuperHeroesBySuperPower(
                    power);

            // then
            Assertions.assertNotNull(listResultDomain);
            Assertions.assertEquals(hlist.size(), listResultDomain.size());
            Assertions.assertEquals(hlist.get(0).getId(), listResultDomain.get(0).getId());
            Assertions.assertEquals(hlist.get(1).getId(), listResultDomain.get(1).getId());
            Assertions.assertEquals(listResultDomain.get(0).getSuperPower().get(0).getSuperPower(), power);
            Assertions.assertEquals(listResultDomain.get(1).getSuperPower().get(0).getSuperPower(), power);
        }

    }

    /**
     * Tests for findById method
     */

    @Nested
    class findByIdTest {
        @Test
        void givenNullId_thenThrowException() {
            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> superHeroesApplicationService.findSuperHeroById(null));
            Assertions.assertEquals(ID_MANDATORY, ex.getMessage());
        }

        @Test
        void givenNotMatchingId_thenThrowNotFoundException() {
            Mockito.when(getSuperHeroesUseCase.findSuperHeroById(ArgumentMatchers.anyLong()))
                    .thenReturn(Optional.empty());
            final NotFoundException ex = Assertions.assertThrows(NotFoundException.class,
                    () -> superHeroesApplicationService.findSuperHeroById(SuperHeroFactory.HERO_ID));
            Assertions.assertEquals("Not found hero with id " + SuperHeroFactory.HERO_ID, ex.getMessage());
        }

        @Test
        void givenMatchingId_thenReturnsTheHero() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));
            final SuperHeroDomain h1 = SuperHeroFactory.getDO();
            Mockito.when(getSuperHeroesUseCase.findSuperHeroById(ArgumentMatchers.anyLong()))
                    .thenReturn(Optional.of(h1));
            // when
            final SuperHeroDomain resultDomain = superHeroesApplicationService.findSuperHeroById(
                    SuperHeroFactory.HERO_ID);
            // then
            Assertions.assertNotNull(resultDomain);
            Assertions.assertEquals(h1.getId(), resultDomain.getId());
            Assertions.assertEquals(h1.getName(), resultDomain.getName());
            Assertions.assertEquals(h1.getSuperPower().size(), resultDomain.getSuperPower().size());
            Assertions.assertEquals(h1.getSuperPower().get(0).getSuperPower(),
                    resultDomain.getSuperPower().get(0).getSuperPower());
        }
    }

    @Nested
    class addSuperPowerTest {

        @Test
        void givenNullParameters_ThenThrowsException() {
            //given

            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> superHeroesApplicationService.addSuperPower(null, SuperPower.TELEKINESIS));
            Assertions.assertEquals(ID_MANDATORY, ex.getMessage());

            final BusinessRuleViolatedException ex2 = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> superHeroesApplicationService.addSuperPower(SuperHeroFactory.HERO_ID, null));
            Assertions.assertEquals(POWER_MANDATORY, ex2.getMessage());

        }

        @Test
        void givenNonExistingHero_ThenThrowsNotFoundException() {
            //given

            Mockito.when(getSuperHeroesUseCase.findSuperHeroById(ArgumentMatchers.anyLong()))
                    .thenReturn(Optional.empty());

            final NotFoundException ex = Assertions.assertThrows(NotFoundException.class,
                    () -> superHeroesApplicationService.addSuperPower(SuperHeroFactory.HERO_ID,
                            SuperPower.TELEKINESIS));
            Assertions.assertEquals("Not found hero with id " + SuperHeroFactory.HERO_ID, ex.getMessage());

        }

        @Test
        void givenExistingHero_WhenAlreadyHasThatPower_ThenThrowsAlreadyExistException() {
            //given

            final SuperHeroDomain h1 = SuperHeroFactory.getDO();

            Mockito.when(getSuperHeroesUseCase.findSuperHeroById(ArgumentMatchers.anyLong()))
                    .thenReturn(Optional.of(h1));

            final AlreadyExistException ex = Assertions.assertThrows(AlreadyExistException.class,
                    () -> superHeroesApplicationService.addSuperPower(SuperHeroFactory.HERO_ID,
                            SuperPower.TELEKINESIS));
            Assertions.assertEquals("This Hero already owns that superpower: " + SuperPower.TELEKINESIS,
                    ex.getMessage());

        }

        @Test
        void givenExistingHero_WhenDoesNotHaveThatPower_ThenAddPower() {
            //given
            final SuperHeroDomain h1 = SuperHeroFactory.getDO();

            Mockito.when(getSuperHeroesUseCase.findSuperHeroById(ArgumentMatchers.anyLong()))
                    .thenReturn(Optional.of(h1));

            //when
            SuperHeroDomain result = superHeroesApplicationService.addSuperPower(SuperHeroFactory.HERO_ID,
                    SuperPower.EXTRAORDINARY_INTELLIGENCE);

            //then
            Mockito.verify(createSuperPowerUseCase, Mockito.times(1))
                    .addSuperPower(superPowerDomainArgumentCaptor.capture());
            Assertions.assertEquals(SuperPower.EXTRAORDINARY_INTELLIGENCE,
                    superPowerDomainArgumentCaptor.getValue().getSuperPower());
            Assertions.assertNotNull(result);
            List<SuperPower> hiroPowers = result.getSuperPower().stream().map(p -> p.getSuperPower()).distinct()
                    .toList();
            //TODO
            // Has to Mock superHeroRepository.findById the second time to return an updated hero
            //Assertions.assertTrue(hiroPowers.contains(SuperPower.EXTRAORDINARY_INTELLIGENCE));
        }

    }

    @Nested
    class createSuperHeroTest {

        @Test
        void givenNullParameters_ThenThrowsException() {
            //given
            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> superHeroesApplicationService.createSuperHero(null));
            Assertions.assertEquals(HERO_MANDATORY, ex.getMessage());

        }

        @Test
        void givenNotNullDto_WhenDtoIsNotValid_ThenThrowsException() {
            //given
            SuperHeroDTO noName = SuperHeroFactory.getDTO();
            noName.setName(null);
            SuperHeroDTO noPower = SuperHeroFactory.getDTO();
            noPower.setSuperPower(null);
            SuperHeroDTO alreadyExisting = SuperHeroFactory.getDTO();
            SuperHeroDomain alreadyExistingD = SuperHeroFactory.getDO();
            alreadyExisting.setName("alreadyExisting");
            alreadyExistingD.setName("alreadyExisting");

            Mockito.when(getSuperHeroesUseCase.findFirstByName("alreadyExisting"))
                    .thenReturn(Optional.of(alreadyExistingD));
            //    Mockito.when(getSuperHeroesUseCase.findFirstByName(noPower.getName())).thenReturn(Optional.empty());

            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> superHeroesApplicationService.createSuperHero(noName));
            Assertions.assertEquals(NAME_EMPTY, ex.getMessage());

            final BusinessRuleViolatedException ex2 = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> superHeroesApplicationService.createSuperHero(noPower));
            Assertions.assertEquals(POWERS_EMPTY, ex2.getMessage());

            final AlreadyExistException ex3 = Assertions.assertThrows(AlreadyExistException.class,
                    () -> superHeroesApplicationService.createSuperHero(alreadyExisting));
            Assertions.assertTrue(ex3.getMessage().contains("This Hero already exists"));

        }

        @Test
        void givenValidatedDto_ThenCreateHero() {
            //given

            SuperHeroDTO validDto = SuperHeroFactory.getDTO();
            SuperHeroDomain validDomain = SuperHeroFactory.getDO();

            Mockito.when(getSuperHeroesUseCase.findFirstByName(ArgumentMatchers.anyString()))
                    .thenReturn(Optional.empty());
            Mockito.when(getSuperHeroesUseCase.findSuperHeroById(ArgumentMatchers.anyLong()))
                    .thenReturn(Optional.of(validDomain));

            Mockito.when(createSuperHeroUseCase.createSuperHero(validDomain)).thenReturn(validDomain);

            //when
            superHeroesApplicationService.createSuperHero(validDto);

            //then
            Mockito.verify(createSuperHeroUseCase, Mockito.times(1))
                    .createSuperHero(superHeroDomainArgumentCaptor.capture());
            Mockito.verify(createSuperPowerUseCase, Mockito.times(1)).assignAllSuperPowers(ArgumentMatchers.any());

            Assertions.assertEquals(validDto.getId(), superHeroDomainArgumentCaptor.getValue().getId());
            Assertions.assertEquals(validDto.getName(), superHeroDomainArgumentCaptor.getValue().getName());
            Assertions.assertEquals(validDto.getDescription(),
                    superHeroDomainArgumentCaptor.getValue().getDescription());
        }

    }

    @Nested
    class updateSuperHeroTest {

        @Test
        void givenNullParameters_ThenThrowsException() {
            //given
            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> superHeroesApplicationService.updateSuperHero(null, SuperHeroFactory.getDTO()));
            Assertions.assertEquals(ID_MANDATORY, ex.getMessage());

            final BusinessRuleViolatedException ex2 = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> superHeroesApplicationService.updateSuperHero(SuperHeroFactory.HERO_ID, null));
            Assertions.assertEquals(HERO_MANDATORY, ex2.getMessage());

        }

        @Test
        void givenNotNullDto_WhenDtoIsNotValid_ThenThrowsException() {
            //given
            SuperHeroDTO noName = SuperHeroFactory.getDTO();
            noName.setName(null);
            SuperHeroDTO noPower = SuperHeroFactory.getDTO();
            noPower.setSuperPower(null);
            SuperHeroDTO alreadyExisting = SuperHeroFactory.getDTO();
            SuperHeroDomain alreadyExistingD = SuperHeroFactory.getDO(288L);
            alreadyExisting.setName("alreadyExisting");
            alreadyExistingD.setName("alreadyExisting");

            Mockito.when(getSuperHeroesUseCase.findFirstByName("alreadyExisting"))
                    .thenReturn(Optional.of(alreadyExistingD));

            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> superHeroesApplicationService.updateSuperHero(null, SuperHeroFactory.getDTO()));
            Assertions.assertEquals(ID_MANDATORY, ex.getMessage());

            final BusinessRuleViolatedException ex2 = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> superHeroesApplicationService.updateSuperHero(SuperHeroFactory.HERO_ID, noName));
            Assertions.assertEquals(NAME_EMPTY, ex2.getMessage());

            final BusinessRuleViolatedException ex3 = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> superHeroesApplicationService.updateSuperHero(SuperHeroFactory.HERO_ID, noPower));
            Assertions.assertEquals(POWERS_EMPTY, ex3.getMessage());

            final AlreadyExistException ex4 = Assertions.assertThrows(AlreadyExistException.class,
                    () -> superHeroesApplicationService.updateSuperHero(SuperHeroFactory.HERO_ID, alreadyExisting));
            Assertions.assertTrue(ex4.getMessage().contains("This Hero already exists"));

        }

        @Test
            //@Disabled
        void givenValidatedDto_ThenUpdatesHero() {
            //given

            SuperHeroDTO validDto = SuperHeroFactory.getDTO();
            SuperHeroDomain validDomain = SuperHeroFactory.getDO();

            Mockito.when(getSuperHeroesUseCase.findFirstByName(ArgumentMatchers.anyString()))
                    .thenReturn(Optional.empty());
            Mockito.when(getSuperHeroesUseCase.findSuperHeroById(ArgumentMatchers.anyLong()))
                    .thenReturn(Optional.of(validDomain));
            //TODO Must fix inmutable collection when Mock
            //when
            superHeroesApplicationService.updateSuperHero(SuperHeroFactory.HERO_ID, validDto);

            //then
            Mockito.verify(updateSuperHeroeUseCase, Mockito.times(1))
                    .updateSuperHero(superHeroDomainArgumentCaptor.capture());
            Mockito.verify(createSuperPowerUseCase, Mockito.times(1)).assignAllSuperPowers(ArgumentMatchers.any());

            Assertions.assertEquals(validDto.getId(), superHeroDomainArgumentCaptor.getValue().getId());
            Assertions.assertEquals(validDto.getName(), superHeroDomainArgumentCaptor.getValue().getName());
            Assertions.assertEquals(validDto.getDescription(),
                    superHeroDomainArgumentCaptor.getValue().getDescription());
        }

    }

    /**
     * deleteSuperHeroById test cases
     */
    @Nested
    @DisplayName("deleteSuperHeroById test cases")
    class deleteSuperHeroByIdTest {
        @Test
        @DisplayName("Throws exception when hero ID is null")
        void givenNullId_thenThrowsException() {
            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> superHeroesApplicationService.deleteSuperHeroById(null));
            Assertions.assertEquals(ID_MANDATORY, ex.getMessage());
        }

        @Test
        @DisplayName("Throws exception when hero ID does not exist")
        void givenNonExistingId_thenThrowsException() {

            Mockito.when(getSuperHeroesUseCase.findSuperHeroById(SuperHeroFactory.HERO_ID))
                    .thenReturn(Optional.empty());

            final NotFoundException ex = Assertions.assertThrows(NotFoundException.class,
                    () -> superHeroesApplicationService.deleteSuperHeroById(SuperHeroFactory.HERO_ID));
            Assertions.assertEquals("Not found hero with id " + SuperHeroFactory.HERO_ID, ex.getMessage());
        }

        @Test
        void givenExistingHero_thenDeletesIt() {

            final SuperHeroDomain heroToDelete = SuperHeroFactory.getDO();

            Mockito.when(getSuperHeroesUseCase.findSuperHeroById(SuperHeroFactory.HERO_ID))
                    .thenReturn(Optional.of(heroToDelete));

            superHeroesApplicationService.deleteSuperHeroById(SuperHeroFactory.HERO_ID);

            Mockito.verify(deleteSuperPowerUseCase, Mockito.times(1)).deleteAllBySuperheroId(SuperHeroFactory.HERO_ID);
            Mockito.verify(deleteSuperHeroesUseCase, Mockito.times(1)).deleteSuperHeroById(SuperHeroFactory.HERO_ID);

        }

    }

    /**
     * deleteAllSuperHeros test cases
     */
    @Nested
    @DisplayName("deleteAllSuperHeros test cases")
    class deleteAllSuperHeros {
        @Test
        @DisplayName("Does nothing when no existing registers")
        void givenNonExistingRegisters_thenDoNothing() {

//            Mockito.when(getSuperPowersUseCase.findAllSuperPowers()).thenReturn(List.of());
//            Mockito.when(getSuperHeroesUseCase.getAllSuperHeroes()).thenReturn(List.of());

            superHeroesApplicationService.deleteAllSuperHeroes();

            Mockito.verify(deleteSuperPowerUseCase, Mockito.times(0)).deleteAll();
            Mockito.verify(deleteSuperHeroesUseCase, Mockito.times(0)).deleteAllSuperHeroes();
        }

        @Test
        @DisplayName("Deletes all when existing registers")
        void givenExistingRegisters_thenDeletesAll() {

            final SuperHeroDomain heroToDelete = SuperHeroFactory.getDO();
            final SuperPowerDomain powerToDelete = SuperHeroFactory.getPowerDO(SuperHeroFactory.HERO_ID);

            Mockito.when(getSuperPowersUseCase.findAllSuperPowers()).thenReturn(List.of(powerToDelete));
            Mockito.when(getSuperHeroesUseCase.getAllSuperHeroes()).thenReturn(List.of(heroToDelete));

            superHeroesApplicationService.deleteAllSuperHeroes();

            Mockito.verify(deleteSuperPowerUseCase, Mockito.times(1)).deleteAll();
            Mockito.verify(deleteSuperHeroesUseCase, Mockito.times(1)).deleteAllSuperHeroes();

        }

    }

}
