package com.w2m.heroestest.restapi.persistence.repositories;

import com.w2m.heroestest.restapi.persistence.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author jruizh
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

   /* @Query(value = """
            select t from Token t inner join AuthUser u\s
            on t.user.id = u.id\s
            where u.id = :id and (t.expired = false or t.revoked = false)\s
            """)
    List<Token> findAllValidTokenByUser(Integer id);*/

    Optional<Token> findByToken(String token);
}