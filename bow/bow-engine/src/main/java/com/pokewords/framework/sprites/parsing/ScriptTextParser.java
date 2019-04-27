package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.ParsingException;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.GameEffect;

public interface ScriptTextParser {

    /**
     *
     * @param scriptText String to be parsed.
     * @return Script object.
     * @throws ParsingException If the script's grammar is wrong.
     */
    Script parse(String scriptText);

}
