package com.pokewords.framework.engine.exceptions;

public class KeyValuePairsException extends GameEngineException {
    public KeyValuePairsException(String message) {
        super(message);
    }

    public KeyValuePairsException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeyValuePairsException(Throwable cause) {
        super(cause);
    }

    public KeyValuePairsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
