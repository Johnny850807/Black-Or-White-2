package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.ParsingException;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

import java.util.function.BiConsumer;

public interface FrameStateMachineScriptParser {

    /**
     * {@code listener} defaults to an empty listener
     *
     * @see FrameStateMachineScriptParser#parse(Script, OnParsingFrameListener)
     */
    default FrameStateMachineComponent parse(Script script) {
        return this.parse(script,
                // do nothing to the app state world by default
                (segment) ->
                        (world, sprite) -> {
                        });
    }

    /**
     * @param script the DSL of the FrameStateMachine supported by the Game Engine, see \docs\FSM Script Definition.md
     * @return the FrameStateMachine defined by the script
     * @throws ParsingException If the script's grammar is incorrect
     */
    FrameStateMachineComponent parse(Script script, OnParsingFrameListener listener);

    interface OnParsingFrameListener {
        /**
         * Callback method triggered while the parser parsing each frame segment in a sequence.
         * This is the api allowing the game designer affecting the game logic scripted in the frame segments to the AppStateWorld .
         *
         * @param segment the being parsed segment
         * @return the action that this frame'd like to apply in the given AppStateWorld.
         */
        BiConsumer<AppStateWorld, Sprite> onParsing(FrameSegment frameSegment);
    }


}
