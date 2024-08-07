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
    USER(
            Set.of(
                    BASIC_READ_BASIC
            )
    ),
    BASIC(
            Set.of(
                    BASIC_READ_BASIC
            )

    ),

    PREMIUM(
            Set.of(
                    PREMIUM_READ_PREMIUM,
                    PREMIUM_READ_BASIC
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

    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }

}
