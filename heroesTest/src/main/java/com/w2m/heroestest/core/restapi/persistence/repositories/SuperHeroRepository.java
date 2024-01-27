package com.w2m.heroestest.core.restapi.persistence.repositories;

import com.w2m.heroestest.core.restapi.persistence.entities.SuperHeroEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuperHeroRepository extends JpaRepository<SuperHeroEntity, Long> {
    Optional<SuperHeroEntity> findById(final Long id);

    List<SuperHeroEntity> findByNameContaining(final String name);

    Page<SuperHeroEntity> findByNameContainingIgnoreCase(final String name, Pageable pageable);

    Optional<SuperHeroEntity> findFirstByNameIgnoreCase(final String name);

}