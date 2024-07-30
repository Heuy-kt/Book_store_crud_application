package com.prunnytest.bookstore.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission{

    USER_READ_PREMIUM("user:premium"),
    USER_READ_BASIC("user:basic"),

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_WRITE("admin:post"),

    MANAGER_WRITE("management:post"),
    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_DELETE("management:delete");

    private final String permission;
}
