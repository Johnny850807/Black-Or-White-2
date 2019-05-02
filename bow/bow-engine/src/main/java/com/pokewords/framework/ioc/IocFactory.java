package com.pokewords.framework.ioc;

import com.pokewords.framework.engine.UserConfig;
import com.pokewords.framework.sprites.PrototypeFactory;
import com.pokewords.framework.sprites.SpriteBuilder;
import com.pokewords.framework.sprites.components.FrameFactory;
import com.pokewords.framework.sprites.parsing.Script;
import com.pokewords.framework.views.InputManager;
import com.pokewords.framework.views.SoundPlayer;

public interface IocFactory {

	FrameFactory frameFactory();

	PrototypeFactory prototypeFactory();

	SoundPlayer soundPlayer();

	UserConfig userConfig();

	InputManager inputManager();

	SpriteBuilder spriteBuilder();
}
