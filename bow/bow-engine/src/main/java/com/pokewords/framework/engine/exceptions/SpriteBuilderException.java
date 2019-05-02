package com.pokewords.framework.engine.exceptions;

public class SpriteBuilderException extends GameEngineException {
    public SpriteBuilderException(String message) {
        super(message);
    }

    public SpriteBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpriteBuilderException(Throwable cause) {
        super(cause);
    }

    public SpriteBuilderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
