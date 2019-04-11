package com.pokewords.framework.engine.exceptions;

import java.util.function.Supplier;

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
