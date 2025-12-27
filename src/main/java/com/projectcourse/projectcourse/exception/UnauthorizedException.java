package com.projectcourse.projectcourse.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("User is not authorized");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
