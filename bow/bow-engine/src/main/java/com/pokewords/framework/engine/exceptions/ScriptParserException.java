package com.pokewords.framework.engine.exceptions;

public class ScriptParserException extends SpriteDeclaratorException {
    public ScriptParserException(String message) {
        super(message);
    }

    public ScriptParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScriptParserException(Throwable cause) {
        super(cause);
    }

    public ScriptParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
