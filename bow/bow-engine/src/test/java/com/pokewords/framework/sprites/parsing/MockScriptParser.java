package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.sprites.components.FrameStateMachineComponent;

public class MockScriptParser implements ScriptParser {
    @Override
    public FrameStateMachineComponent parse(Script script, OnParsingFrameListener listener) {
        return null;
    }
}
