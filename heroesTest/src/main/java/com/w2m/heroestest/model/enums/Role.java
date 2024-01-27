package com.w2m.heroestest.model.enums;

import lombok.RequiredArgsConstructor;

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