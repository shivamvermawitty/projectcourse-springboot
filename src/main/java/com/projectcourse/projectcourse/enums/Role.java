package com.projectcourse.projectcourse.enums;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN"),
    STUDENT("STUDENT"),
    TEACHER("TEACHER");

    private final String role;

    Role(String role) {
        this.role = role;
    }
}