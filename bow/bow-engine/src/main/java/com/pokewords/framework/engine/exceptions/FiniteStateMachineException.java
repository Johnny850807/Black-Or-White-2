package com.pokewords.framework.engine.exceptions;

public class FiniteStateMachineException extends GameEngineException {
    public FiniteStateMachineException(String message) {
        super(message);
    }

    public FiniteStateMachineException(String message, Throwable cause) {
        super(message, cause);
    }

    public FiniteStateMachineException(Throwable cause) {
        super(cause);
    }

    public FiniteStateMachineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
