package com.prunnytest.bookstore.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.prunnytest.bookstore.model.enums.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Roles {
    BASIC(Set.of(USER_READ_BASIC)),

    PREMIUM(
            Set.of(
                    USER_READ_PREMIUM,
                    USER_READ_BASIC
            )
    ),

    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_WRITE,

                    MANAGER_WRITE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE
            )
    ),

    MANAGER(
            Set.of(
                    MANAGER_WRITE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE
            )
    );

    private final Set<Permission> permissions;
    public List<SimpleGrantedAuthority> gratedUserAuthorities(){
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .toList();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }


}
