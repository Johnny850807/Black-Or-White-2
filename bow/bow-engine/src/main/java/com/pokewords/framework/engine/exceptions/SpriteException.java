package com.pokewords.framework.engine.exceptions;

public class SpriteException extends GameEngineException {
    public SpriteException(String message) {
        super(message);
    }

    public SpriteException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpriteException(Throwable cause) {
        super(cause);
    }

    public SpriteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
