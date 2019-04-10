package com.pokewords.framework.ioc;

import com.pokewords.framework.engine.UserConfig;
import com.pokewords.framework.sprites.PrototypeFactory;
import com.pokewords.framework.sprites.components.FrameFactory;
import com.pokewords.framework.sprites.components.parsing.FrameStateMachineScriptParser;
import com.pokewords.framework.views.SoundPlayer;

public interface IocFactory {

	FrameFactory frameFactory();

	FrameStateMachineScriptParser frameStateMachineScriptParser();

	PrototypeFactory prototypeFactory();

	SoundPlayer soundPlayer();

	UserConfig userConfig();

}
