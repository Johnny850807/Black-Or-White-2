package com.pokewords.framework.engine.exceptions;

public class LinScriptParserException extends SpriteDeclaratorException {
    public LinScriptParserException(String message) {
        super(message);
    }

    public LinScriptParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public LinScriptParserException(Throwable cause) {
        super(cause);
    }

    public LinScriptParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
