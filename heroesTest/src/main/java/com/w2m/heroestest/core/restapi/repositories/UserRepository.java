package com.w2m.heroestest.core.restapi.repositories;

import java.util.Optional;

import com.w2m.heroestest.core.restapi.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}