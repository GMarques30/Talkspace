package com.talkspace.account.application.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends RuntimeException {
    public HttpStatus status;

    public InvalidCredentialsException() {
        super("Invalid credentials.");
        this.status = HttpStatus.UNAUTHORIZED;

    }
}
