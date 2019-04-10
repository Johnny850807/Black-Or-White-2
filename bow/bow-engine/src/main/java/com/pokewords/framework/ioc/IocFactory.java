package com.pokewords.framework.ioc;

import com.pokewords.framework.sprites.components.FrameFactory;
import com.pokewords.framework.sprites.components.parsing.FrameStateMachineScriptParser;
import com.pokewords.framework.sprites.PrototypeFactory;
import com.pokewords.framework.views.SoundPlayer;
import com.pokewords.framework.engine.UserConfig;

public interface IocFactory {

	FrameFactory frameFactory();

	FrameStateMachineScriptParser prototypeParser();

	PrototypeFactory prototypeFactory();

	SoundPlayer soundPlayer();

	UserConfig userConfig();

}
