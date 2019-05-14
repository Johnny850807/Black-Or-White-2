package com.pokewords.framework.engine.exceptions;

public class SegmentException extends GameEngineException {
    public SegmentException(String message) {
        super(message);
    }

    public SegmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public SegmentException(Throwable cause) {
        super(cause);
    }

    public SegmentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
