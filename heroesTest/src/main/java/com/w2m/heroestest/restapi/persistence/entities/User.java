package com.w2m.heroestest.restapi.persistence.entities;

import com.w2m.heroestest.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author jruizh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AUTH_USER")
public class User /*implements UserDetails*/ {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    /**@Override public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
    }

     @Override public String getPassword() {
     return password;
     }

     @Override public String getUsername() {
     return email;
     }

     @Override public boolean isAccountNonExpired() {
     return true;
     }

     @Override public boolean isAccountNonLocked() {
     return true;
     }

     @Override public boolean isCredentialsNonExpired() {
     return true;
     }

     @Override public boolean isEnabled() {
     return true;
     }
     **/
}
