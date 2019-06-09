package com.pokewords.framework.engine.exceptions;

public class NodeException extends GameEngineException {
    public NodeException(String message) {
        super(message);
    }

    public NodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NodeException(Throwable cause) {
        super(cause);
    }

    public NodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
