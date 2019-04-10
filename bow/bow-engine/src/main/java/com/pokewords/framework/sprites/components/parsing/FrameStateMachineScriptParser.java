package com.pokewords.framework.sprites.components.parsing;

import com.pokewords.framework.sprites.components.FrameStateMachineComponent;

public interface FrameStateMachineScriptParser {
	/**
	 * @param script the DSL of the FrameStateMachine supported by the Game Engine, see \docs\FSM Script Definition.md
	 * @return the FrameStateMachine defined by the script
	 * @throws Exception
	 */
	FrameStateMachineComponent parse(String script);
}
