package com.pokewords.framework.engine.exceptions;

public class SpriteDeclarationException extends GameEngineException {


    public SpriteDeclarationException(String message) {
        super(message);
    }

    public SpriteDeclarationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpriteDeclarationException(Throwable cause) {
        super(cause);
    }

    public SpriteDeclarationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
