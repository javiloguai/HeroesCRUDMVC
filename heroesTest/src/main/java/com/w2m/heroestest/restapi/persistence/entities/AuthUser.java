package com.w2m.heroestest.restapi.persistence.entities;

import java.util.Collection;
import java.util.List;

import com.w2m.heroestest.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AUTH_USER", uniqueConstraints = { @UniqueConstraint(columnNames = { "USERNAME" }) })
public class AuthUser implements UserDetails {

    @Id
    @GeneratedValue
    Integer id;

    @Basic
    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

//    private String firstname;
//
//    private String lastname;
//
//    private String email;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;
//
//    @OneToMany(mappedBy = "user")
//    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
        //return role.getAuthorities();
    }

    @Override public String getPassword() {
        return password;
    }

    @Override public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}