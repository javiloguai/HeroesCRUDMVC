package com.w2m.heroestest.restapi.persistence.repositories;

import com.w2m.heroestest.restapi.persistence.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author jruizh
 */
@Repository
public interface UserRepository extends JpaRepository<AuthUser, Integer> {
    Optional<AuthUser> findByUsername(String username);

}
