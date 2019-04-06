package com.pokewords.framework.ioc;

import com.pokewords.framework.sprites.components.FrameFactory;
import com.pokewords.framework.engine.asm.loader.scriptparser.PrototypeParser;
import com.pokewords.framework.sprites.PrototypeFactory;
import com.pokewords.framework.views.SoundPlayer;
import com.pokewords.framework.engine.UserConfig;

public interface IocFactory {

	FrameFactory frameFactory();

	PrototypeParser prototypeParser();

	PrototypeFactory prototypeFactory();

	SoundPlayer soundPlayer();

	UserConfig userConfig();

}
