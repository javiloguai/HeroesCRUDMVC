package com.w2m.heroestest.restapi.services.impl;

import com.w2m.heroestest.config.exception.AlreadyExistException;
import com.w2m.heroestest.config.exception.BusinessRuleViolatedException;
import com.w2m.heroestest.config.exception.NotFoundException;
import com.w2m.heroestest.factories.SuperHeroFactory;
import com.w2m.heroestest.model.domain.SuperHeroDomain;
import com.w2m.heroestest.model.dto.SuperHeroDTO;
import com.w2m.heroestest.model.enums.SuperPower;
import com.w2m.heroestest.restapi.persistence.entities.HeroSuperPowerEntity;
import com.w2m.heroestest.restapi.persistence.entities.SuperHeroEntity;
import com.w2m.heroestest.restapi.persistence.mappers.SuperHeroDataBaseMapper;
import com.w2m.heroestest.restapi.persistence.repositories.HeroSuperPowerRepository;
import com.w2m.heroestest.restapi.persistence.repositories.SuperHeroRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

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
 *
 */
//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(SuperHeroesServiceImpl.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration
//@WithMockUser(username="admin",roles={"USER","ADMIN"})
public class SuperHeroesServiceImplTest {

	private final static String ID_MANDATORY="Id field is Mandatory";
	private final static String NAME_EMPTY="Hero name cannot be empty";
	private final static String POWERS_EMPTY="Hero superpowers list cannot be empty";
	private final static String PAGE_MANDATORY="page info is Mandatory";
	private final static String NAME_MANDATORY="name field is Mandatory";
	private final static String POWER_MANDATORY="power field is Mandatory";
	private final static String HERO_MANDATORY="The hero Object is Mandatory";

	@InjectMocks
	private SuperHeroesServiceImpl superHeroesService;

	@MockBean
	private SuperHeroRepository superHeroRepository;

	@MockBean
	private HeroSuperPowerRepository heroSuperPowerRepository;

	@Captor
	private ArgumentCaptor<HeroSuperPowerEntity> heroSuperPowerEntityCaptor;

	@Captor
	private ArgumentCaptor<SuperHeroEntity> superHeroEntityCaptor;

	/**
	 * Tests for getAllSuperHeroes method
	 */
	@Nested
	class getAllSuperHeroesTest {

		@Test
		void givenNullPage_thenThrowException() {

			final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
					() -> superHeroesService.pageAllSuperHeroes(null));
			Assertions.assertEquals(PAGE_MANDATORY, ex.getMessage());

		}

		@Test
		void givenNonExistingHeroes_thenReturnEmptyPage() {
			// given
			//MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

			final List<SuperHeroEntity> hlist = List.of();

			final Pageable pageable = PageRequest.of(0, 20);
			final Page<SuperHeroEntity> pageResult = new PageImpl<>(hlist, pageable, 1);
			Mockito.when(superHeroRepository.findAll(any(Pageable.class))).thenReturn(pageResult);

			// when
			final Page<SuperHeroDomain> pageResultDomain = superHeroesService.pageAllSuperHeroes(pageable);

			// then
			Assertions.assertNotNull(pageResultDomain);
			Assertions.assertTrue(pageResultDomain.isEmpty());
			Assertions.assertEquals(hlist.size(), pageResultDomain.getTotalElements());

		}

		@Test
		void givenNonExistingHeroes_thenReturnEmptyList() {
			// given
			//MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

			final List<SuperHeroEntity> hlist = List.of();

			Mockito.when(superHeroRepository.findAll()).thenReturn(hlist);


			// when
			final List<SuperHeroDomain> listResultDomain = superHeroesService.getAllSuperHeroes();

			// then
			Assertions.assertNotNull(listResultDomain);
			Assertions.assertTrue(listResultDomain.isEmpty());
			Assertions.assertEquals(hlist.size(), listResultDomain.size());
		}

		@Test
		void givenExistingHeroes_thenReturnAllPagedHeroes() {
			// given
			//MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

			final SuperHeroEntity h1 = SuperHeroFactory.getEntity(1L);
			final SuperHeroEntity h2 = SuperHeroFactory.getEntity(2L);

		    final List<SuperHeroEntity> hlist = List.of(h1, h2);

			final Pageable pageable = PageRequest.of(0, 20);
			final Page<SuperHeroEntity> pageResult = new PageImpl<>(hlist, pageable, 1);
			Mockito.when(superHeroRepository.findAll(any(Pageable.class))).thenReturn(pageResult);

			// when
			final Page<SuperHeroDomain> pageResultDomain = superHeroesService.pageAllSuperHeroes(pageable);

			// then
			Assertions.assertEquals(hlist.size(), pageResultDomain.getTotalElements());
			Assertions.assertEquals(hlist.get(0).getId(), pageResultDomain.toList().get(0).getId());
			Assertions.assertEquals(hlist.get(1).getId(), pageResultDomain.toList().get(1).getId());
		}

		@Test
		void givenExistingHeroes_thenReturnAllHeroes() {
			// given
			//MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

			final SuperHeroEntity h1 = SuperHeroFactory.getEntity(1L);
			final SuperHeroEntity h2 = SuperHeroFactory.getEntity(2L);

			final List<SuperHeroEntity> hlist = List.of(h1, h2);

			Mockito.when(superHeroRepository.findAll()).thenReturn(hlist);

			// when
			final List<SuperHeroDomain> listResultDomain = superHeroesService.getAllSuperHeroes();

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

			final List<SuperHeroEntity> hlist = List.of();

			final Pageable pageable = PageRequest.of(0, 20);

			final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
					() -> superHeroesService.getAllSuperHeroesByName(null));
			Assertions.assertEquals(NAME_MANDATORY, ex.getMessage());

			final BusinessRuleViolatedException ex2 = Assertions.assertThrows(BusinessRuleViolatedException.class,
					() -> superHeroesService.pageAllSuperHeroesByName(null,pageable));
			Assertions.assertEquals(NAME_MANDATORY, ex2.getMessage());

		}

		@Test
		void givenNullPage_thenThrowException() {

			final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
					() -> superHeroesService.pageAllSuperHeroesByName("name",null));
			Assertions.assertEquals(PAGE_MANDATORY, ex.getMessage());

		}

		@Test
		void givenNonMatchingHeroes_thenReturnEmptyPage() {
			// given
			//MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

			final List<SuperHeroEntity> hlist = List.of();

			final Pageable pageable = PageRequest.of(0, 20);
			final Page<SuperHeroEntity> pageResult = new PageImpl<>(hlist, pageable, 1);
			Mockito.when(superHeroRepository.findByNameContainingIgnoreCase(ArgumentMatchers.any(String.class),ArgumentMatchers.any(Pageable.class))).thenReturn(pageResult);

			// when
			final Page<SuperHeroDomain> pageResultDomain = superHeroesService.pageAllSuperHeroesByName("XXX",pageable);

			// then
			Assertions.assertNotNull(pageResultDomain);
			Assertions.assertTrue(pageResultDomain.isEmpty());
			Assertions.assertEquals(hlist.size(), pageResultDomain.getTotalElements());

		}

		@Test
		void givenNonMatchingHeroes_thenReturnEmptyList() {
			// given
			//MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

			final List<SuperHeroEntity> hlist = List.of();

			Mockito.when(superHeroRepository.findByNameContaining(ArgumentMatchers.any(String.class))).thenReturn(hlist);

			// when
			final List<SuperHeroDomain> listResultDomain = superHeroesService.getAllSuperHeroesByName("XXX");

			// then
			Assertions.assertNotNull(listResultDomain);
			Assertions.assertTrue(listResultDomain.isEmpty());
			Assertions.assertEquals(hlist.size(), listResultDomain.size());
		}

		@Test
		void givenMatchingHeroes_thenReturnAllMatchingPagedHeroes() {
			// given
			//MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

			final SuperHeroEntity h1 = SuperHeroFactory.getEntity(1L);
			final SuperHeroEntity h2 = SuperHeroFactory.getEntity(2L);

			final List<SuperHeroEntity> hlist = List.of(h1, h2);

			final Pageable pageable = PageRequest.of(0, 20);
			final Page<SuperHeroEntity> pageResult = new PageImpl<>(hlist, pageable, 1);
			Mockito.when(superHeroRepository.findByNameContainingIgnoreCase(h1.getName(),pageable)).thenReturn(pageResult);

			// when
			final Page<SuperHeroDomain> pageResultDomain = superHeroesService.pageAllSuperHeroesByName(h1.getName(),pageable);

			// then
			Assertions.assertEquals(hlist.size(), pageResultDomain.getTotalElements());
			Assertions.assertEquals(hlist.get(0).getId(), pageResultDomain.toList().get(0).getId());
			Assertions.assertEquals(hlist.get(1).getId(), pageResultDomain.toList().get(1).getId());
		}

		@Test
		void givenMatchingHeroes_thenReturnMatchingHeroes() {
			// given
			//MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

			final SuperHeroEntity h1 = SuperHeroFactory.getEntity(1L);
			final SuperHeroEntity h2 = SuperHeroFactory.getEntity(2L);

			final List<SuperHeroEntity> hlist = List.of(h1, h2);

			Mockito.when(superHeroRepository.findByNameContaining(h1.getName())).thenReturn(hlist);

			// when
			final List<SuperHeroDomain> listResultDomain = superHeroesService.getAllSuperHeroesByName(h1.getName());

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
					() -> superHeroesService.getAllSuperHeroesBySuperPower(null));
			Assertions.assertEquals(POWER_MANDATORY, ex.getMessage());


		}

		@Test
		void givenNonMatchingPower_thenReturnEmptyList() {
			// given
			//MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

			final List<HeroSuperPowerEntity> plist = List.of();
			final List<SuperHeroEntity> hlist = List.of();

			List<Long> heroesIds = plist.stream().map(p -> p.getSuperheroId()).distinct().toList();

			Mockito.when(heroSuperPowerRepository.findBySuperPower(ArgumentMatchers.any(SuperPower.class))).thenReturn(plist);

			Mockito.when(superHeroRepository.findAllById(heroesIds)).thenReturn(hlist);

			// when
			final List<SuperHeroDomain> listResultDomain = superHeroesService.getAllSuperHeroesBySuperPower(SuperPower.MOLECULAR_COMBUSTION);

			// then
			Assertions.assertNotNull(listResultDomain);
			Assertions.assertTrue(listResultDomain.isEmpty());
			Assertions.assertEquals(hlist.size(), listResultDomain.size());
		}

		@Test
		void givenMatchingPower_thenReturnMatchingHeroes() {
			// given
			//MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

			final SuperHeroEntity h1 = SuperHeroFactory.getEntity(1L);
			final SuperHeroEntity h2 = SuperHeroFactory.getEntity(2L);

			SuperPower power = SuperHeroFactory.getPowerEntity(1L).getSuperPower();

			HeroSuperPowerEntity p1 = SuperHeroFactory.getPowerEntity(1L,SuperHeroFactory.POWER_ID);
			HeroSuperPowerEntity p2 = SuperHeroFactory.getPowerEntity(2L,SuperHeroFactory.POWER_ID+1);


			final List<HeroSuperPowerEntity> plist = List.of(p1,p2);
			final List<SuperHeroEntity> hlist = List.of(h1,h2);

			List<Long> heroesIds = plist.stream().map(p -> p.getSuperheroId()).distinct().toList();

			Mockito.when(heroSuperPowerRepository.findBySuperPower(power)).thenReturn(plist);

			Mockito.when(superHeroRepository.findAllById(heroesIds)).thenReturn(hlist);

			// when
			final List<SuperHeroDomain> listResultDomain = superHeroesService.getAllSuperHeroesBySuperPower(power);

			// then
			Assertions.assertNotNull(listResultDomain);
			Assertions.assertEquals(hlist.size(), listResultDomain.size());
			Assertions.assertEquals(hlist.get(0).getId(), listResultDomain.get(0).getId());
			Assertions.assertEquals(hlist.get(1).getId(), listResultDomain.get(1).getId());
			Assertions.assertTrue(listResultDomain.get(0).getSuperPower().get(0).getSuperPower().equals(power));
			Assertions.assertTrue(listResultDomain.get(1).getSuperPower().get(0).getSuperPower().equals(power));
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
					() -> superHeroesService.findById(null));
			Assertions.assertEquals(ID_MANDATORY, ex.getMessage());

		}

		@Test
		void givenNotMatchingId_thenThrowNotFoundException() {

			Mockito.when(superHeroRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

			final NotFoundException ex = Assertions.assertThrows(NotFoundException.class,
					() -> superHeroesService.findById(SuperHeroFactory.HERO_ID));
			Assertions.assertEquals("Not found hero with id "+SuperHeroFactory.HERO_ID, ex.getMessage());

		}


		@Test
		void givenMatchingId_thenReturnsTheHero() {
			// given
			//MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

			final SuperHeroEntity h1 = SuperHeroFactory.getEntity();


			Mockito.when(superHeroRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(h1));

			SuperHeroDomain hd = SuperHeroDataBaseMapper.INSTANCE.entityToDomain(h1);


			// when
			final SuperHeroDomain resultDomain = superHeroesService.findById(SuperHeroFactory.HERO_ID);

			// then
			Assertions.assertNotNull(resultDomain);
			Assertions.assertEquals(h1.getId(), resultDomain.getId());
			Assertions.assertEquals(h1.getName(), resultDomain.getName());
			Assertions.assertEquals(h1.getSuperPower().size(), resultDomain.getSuperPower().size());
			Assertions.assertEquals(h1.getSuperPower().get(0).getSuperPower(), resultDomain.getSuperPower().get(0).getSuperPower());

		}


	}

	@Nested
	class addSuperPowerTest {

		@Test
		void givenNullParameters_ThenThrowsException() {
			//given

			final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
					() -> superHeroesService.addSuperPower(null,SuperPower.TELEKINESIS));
			Assertions.assertEquals(ID_MANDATORY, ex.getMessage());

			final BusinessRuleViolatedException ex2 = Assertions.assertThrows(BusinessRuleViolatedException.class,
					() -> superHeroesService.addSuperPower(SuperHeroFactory.HERO_ID,null));
			Assertions.assertEquals(POWER_MANDATORY, ex2.getMessage());

		}

		@Test
		void givenNonExistingHero_ThenThrowsNotFoundException() {
			//given

			Mockito.when(superHeroRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

			final NotFoundException ex = Assertions.assertThrows(NotFoundException.class,
					() -> superHeroesService.addSuperPower(SuperHeroFactory.HERO_ID,SuperPower.TELEKINESIS));
			Assertions.assertEquals("Not found hero with id "+SuperHeroFactory.HERO_ID, ex.getMessage());

		}

		@Test
		void givenExistingHero_WhenAlreadyHasThatPower_ThenThrowsAlreadyExistException() {
			//given

			final SuperHeroEntity h1 = SuperHeroFactory.getEntity();

			Mockito.when(superHeroRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(h1));

			final AlreadyExistException ex = Assertions.assertThrows(AlreadyExistException.class,
					() -> superHeroesService.addSuperPower(SuperHeroFactory.HERO_ID,SuperPower.TELEKINESIS));
			Assertions.assertEquals("This Hero already owns that superpower: "+SuperPower.TELEKINESIS.toString(), ex.getMessage());

		}

		@Test
		void givenExistingHero_WhenDoesNotHaveThatPower_ThenAddPower() {
			//given
			final SuperHeroEntity h1 = SuperHeroFactory.getEntity();

			Mockito.when(superHeroRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(h1));


			//when
			SuperHeroDomain result = superHeroesService.addSuperPower(SuperHeroFactory.HERO_ID,SuperPower.EXTRAORDINARY_INTELLIGENCE);

			//then
			Mockito.verify(heroSuperPowerRepository, Mockito.times(1))
					.saveAndFlush(heroSuperPowerEntityCaptor.capture());
			Assertions.assertEquals(SuperPower.EXTRAORDINARY_INTELLIGENCE, heroSuperPowerEntityCaptor.getValue().getSuperPower());
			Assertions.assertNotNull(result);
			List<SuperPower> hiroPowers =  result.getSuperPower().stream().map(p->p.getSuperPower()).distinct().toList();
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
					() -> superHeroesService.createSuperHero(null));
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
			SuperHeroEntity alreadyExistingE = SuperHeroFactory.getEntity();
			alreadyExisting.setName("alreadyExisting");
			alreadyExistingE.setName("alreadyExisting");

			Mockito.when(superHeroRepository.findFirstByNameIgnoreCase("alreadyExisting")).thenReturn(Optional.of(alreadyExistingE));
			Mockito.when(superHeroRepository.findFirstByNameIgnoreCase(noPower.getName())).thenReturn(Optional.empty());

			final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
					() -> superHeroesService.createSuperHero(noName));
			Assertions.assertEquals(NAME_EMPTY, ex.getMessage());

			final BusinessRuleViolatedException ex2 = Assertions.assertThrows(BusinessRuleViolatedException.class,
					() -> superHeroesService.createSuperHero(noPower));
			Assertions.assertEquals(POWERS_EMPTY, ex2.getMessage());

			final AlreadyExistException ex3 = Assertions.assertThrows(AlreadyExistException.class,
					() -> superHeroesService.createSuperHero(alreadyExisting));
			Assertions.assertTrue(ex3.getMessage().contains("This Hero already exists"));

		}
		@Test
		void givenValidatedDto_ThenCreateHero() {
			//given

			SuperHeroDTO validDto = SuperHeroFactory.getDTO();
			SuperHeroEntity validEntity = SuperHeroFactory.getEntity();

			Mockito.when(superHeroRepository.findFirstByNameIgnoreCase(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
			Mockito.when(superHeroRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(validEntity));

			//when
			superHeroesService.createSuperHero(validDto);

			//then
			Mockito.verify(superHeroRepository, Mockito.times(1))
					.saveAndFlush(superHeroEntityCaptor.capture());
			Mockito.verify(heroSuperPowerRepository, Mockito.times(1)).saveAllAndFlush(ArgumentMatchers.any());

			Assertions.assertEquals(validDto.getId(), superHeroEntityCaptor.getValue().getId());
			Assertions.assertEquals(validDto.getName(), superHeroEntityCaptor.getValue().getName());
			Assertions.assertEquals(validDto.getDescription(), superHeroEntityCaptor.getValue().getDescription());
		}

	}

	@Nested
	class updateSuperHeroTest {

		@Test
		void givenNullParameters_ThenThrowsException() {
			//given
			final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
					() -> superHeroesService.updateSuperHero(null,SuperHeroFactory.getDTO()));
			Assertions.assertEquals(ID_MANDATORY, ex.getMessage());

			final BusinessRuleViolatedException ex2 = Assertions.assertThrows(BusinessRuleViolatedException.class,
					() -> superHeroesService.updateSuperHero(SuperHeroFactory.HERO_ID,null ));
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
			SuperHeroEntity alreadyExistingE = SuperHeroFactory.getEntity();
			alreadyExisting.setName("alreadyExisting");
			alreadyExistingE.setName("alreadyExisting");

			Mockito.when(superHeroRepository.findFirstByNameIgnoreCase("alreadyExisting")).thenReturn(Optional.of(alreadyExistingE));
			Mockito.when(superHeroRepository.findFirstByNameIgnoreCase(noPower.getName())).thenReturn(Optional.empty());
			Mockito.when(superHeroRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(alreadyExistingE));

			final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
					() -> superHeroesService.updateSuperHero(null,SuperHeroFactory.getDTO()));
			Assertions.assertEquals(ID_MANDATORY, ex.getMessage());

			final BusinessRuleViolatedException ex2 = Assertions.assertThrows(BusinessRuleViolatedException.class,
					() -> superHeroesService.updateSuperHero(SuperHeroFactory.HERO_ID,noName));
			Assertions.assertEquals(NAME_EMPTY, ex2.getMessage());

			final BusinessRuleViolatedException ex3 = Assertions.assertThrows(BusinessRuleViolatedException.class,
					() -> superHeroesService.updateSuperHero(SuperHeroFactory.HERO_ID,noPower));
			Assertions.assertEquals(POWERS_EMPTY, ex3.getMessage());

			//TODO Must fix inmutable collection when Mock
			/*final AlreadyExistException ex4 = Assertions.assertThrows(AlreadyExistException.class,
					() -> superHeroesService.updateSuperHero(SuperHeroFactory.HERO_ID,alreadyExisting));
			Assertions.assertTrue(ex4.getMessage().contains("This Hero already exists"));*/

		}
		@Test
		@Disabled
		void givenValidatedDto_ThenUpdatesHero() {
			//given

			SuperHeroDTO validDto = SuperHeroFactory.getDTO();
			SuperHeroEntity validEntity = SuperHeroFactory.getEntity();

			Mockito.when(superHeroRepository.findFirstByNameIgnoreCase(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
			Mockito.when(superHeroRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(validEntity));
			//TODO Must fix inmutable collection when Mock
			//when
			superHeroesService.updateSuperHero(SuperHeroFactory.HERO_ID,validDto);

			//then
			Mockito.verify(superHeroRepository, Mockito.times(1))
					.saveAndFlush(superHeroEntityCaptor.capture());
			Mockito.verify(heroSuperPowerRepository, Mockito.times(1)).saveAllAndFlush(ArgumentMatchers.any());

			Assertions.assertEquals(validDto.getId(), superHeroEntityCaptor.getValue().getId());
			Assertions.assertEquals(validDto.getName(), superHeroEntityCaptor.getValue().getName());
			Assertions.assertEquals(validDto.getDescription(), superHeroEntityCaptor.getValue().getDescription());
		}

	}


//
//	@Test
//    public void testAddCustomerExceptionThrown() {
//        Mockito.doThrow(new RuntimeException("Failed to save customer")).when(customerRepository).save(Mockito.any());
//        CustomerDTO customerDTO = CustomerDTO.builder()
//                .firstName("Rajesh")
//                .surname("Kawali")
//                .mobileNumber(1234567890L)
//                .smoothiePreference("Strawberry")
//                .build();
//        CustomerDTO customerDTOResponse = superHeroesService.addCustomer(customerDTO);
//        Assertions.assertEquals(CustomerDTO.builder().build(), customerDTOResponse);
//    }
//	@Test
//	public void testDeleteCustomerExistingIdReturnsDeleteMessage() {
//		Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(Customer.builder().build()));
//		String response = superHeroesService.deleteCustomer(1L);
//		Assertions.assertEquals(CustomerConstants.DELETE_MESSAGE, response);
//	}
//
//	@Test
//	public void testDeleteCustomerExceptionThrown() {
//		Mockito.doThrow(new RuntimeException()).when(customerRepository).deleteById(Mockito.anyLong());
//		//Mockito.doThrow(new RuntimeException()).when(customerService).customerById(Mockito.anyLong());;
//		//Mockito.when(customerService.customerById(1L)).thenThrow(new RuntimeException("exception"));
//		//Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
//		String response = superHeroesService.deleteCustomer(1L);
//		Assertions.assertNull(response);
//	}
//
//	@Test
//	public void testUpdateCustomerExistingIdReturnsUpdatedCustomerDetails() {
//		Customer customerToSave = Customer.builder()
//				.id(1L)
//				.firstName("Neil")
//				.surname("Jenner")
//				.mobileNumber(1234567890L)
//				.smoothiePreference("Strawberry").build();
//		CustomerDTO customerToUpdate = CustomerDTO.builder()
//				.id(1L)
//				.firstName("Neil")
//				.surname("Jenner")
//				.mobileNumber(1234567890L)
//				.smoothiePreference("Strawberry").build();
//		CustomerDTO customerDetails = CustomerDTO.builder()
//				.id(1L)
//				.firstName("Neil")
//				.surname("Jenner")
//				.mobileNumber(1234567890L)
//				.smoothiePreference("Strawberry").build();
//		CustomerDTO updatedCustomer = CustomerDTO.builder()
//				.id(1L)
//				.firstName("Neil")
//				.surname("Jenner")
//				.mobileNumber(1234567890L)
//				.smoothiePreference("Strawberry").build();
//		Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(Util.dtoToEntity(customerDetails)));
//	    Mockito.when(customerRepository.save(Mockito.any())).thenReturn(customerToSave);
//		CustomerDTO response = superHeroesService.updateCustomer(1L, customerToUpdate);
//		Assertions.assertEquals(updatedCustomer.getId(), response.getId());
//		Assertions.assertEquals(updatedCustomer.getFirstName(), response.getFirstName());
//		Assertions.assertEquals(updatedCustomer.getSurname(), response.getSurname());
//		Assertions.assertEquals(updatedCustomer.getMobileNumber(), response.getMobileNumber());
//		Assertions.assertEquals(updatedCustomer.getSmoothiePreference(), response.getSmoothiePreference());
//	}

}
