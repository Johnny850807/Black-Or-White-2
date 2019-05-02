package com.pokewords.framework.engine.exceptions;

/**
 * The base exception of all module exceptions.
 * @author johnny850807 (waterball)
 */
public class GameEngineException extends RuntimeException {
    public GameEngineException(String message) {
        super(message);
    }

    public GameEngineException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameEngineException(Throwable cause) {
        super(cause);
    }

    public GameEngineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
