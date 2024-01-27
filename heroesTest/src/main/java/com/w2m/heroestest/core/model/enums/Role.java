package com.w2m.heroestest.core.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.w2m.heroestest.core.model.enums.Permission.*;

@RequiredArgsConstructor
public enum Role {
    ADMIN,
    USER//,
//    ADMIN(Set.of(ADMIN_READ, ADMIN_UPDATE, ADMIN_DELETE, ADMIN_CREATE)),
    //    USER(Set.of(USER_READ));

//    @Getter
//    private final Set<Permission> permissions;

//    public List<SimpleGrantedAuthority> getAuthorities() {
//        var authorities = getPermissions().stream()
//                .map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toList());
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
//        return authorities;
//    }
}