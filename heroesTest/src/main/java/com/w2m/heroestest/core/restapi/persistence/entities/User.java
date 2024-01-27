package com.w2m.heroestest.core.restapi.persistence.entities;

import java.util.Collection;
import java.util.List;

import com.w2m.heroestest.core.model.enums.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER", uniqueConstraints = { @UniqueConstraint(columnNames = { "USERNAME" }) })
public class User implements UserDetails {

    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
//    @GenericGenerator(name = "native", strategy = "native")
//    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Basic
    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "LASTNAME", nullable = false)
    private String lastname;

    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
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