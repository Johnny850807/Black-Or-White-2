package com.pokewords.framework.engine.exceptions;

public class NoSuchPrototypeException extends GameEngineException {
    public NoSuchPrototypeException(String message) {
        super(message);
    }

    public NoSuchPrototypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchPrototypeException(Throwable cause) {
        super(cause);
    }

    public NoSuchPrototypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
