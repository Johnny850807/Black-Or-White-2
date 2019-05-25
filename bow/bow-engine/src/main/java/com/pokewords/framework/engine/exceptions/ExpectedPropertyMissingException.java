package com.pokewords.framework.engine.exceptions;

/**
 * Thrown to indicate the a property is missing.
 * @author johnny850807 (waterball)
 */
public class ExpectedPropertyMissingException extends GameEngineException {

    public ExpectedPropertyMissingException(Object propertyKey) {
        super(String.format("The property of key '%s' is missing.", propertyKey));
    }
}
