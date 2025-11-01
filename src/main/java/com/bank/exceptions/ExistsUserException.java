package com.bank.exceptions;

public class ExistsUserException extends Exception{
    public ExistsUserException() {
        super();
    }

    public ExistsUserException(String message) {
        super(message);
    }

    public ExistsUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistsUserException(Throwable cause) {
        super(cause);
    }

    protected ExistsUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
