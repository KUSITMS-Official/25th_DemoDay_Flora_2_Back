package com.lookatthis.flora.exception;

public class PasswordErrorException extends IllegalArgumentException {
    public PasswordErrorException() {
        super();
    }

    public PasswordErrorException(String msg) {
        super(msg);
    }
}
