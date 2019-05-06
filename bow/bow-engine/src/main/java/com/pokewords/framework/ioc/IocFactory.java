package com.pokewords.framework.ioc;

import com.pokewords.framework.engine.UserConfig;
import com.pokewords.framework.sprites.PrototypeFactory;
import com.pokewords.framework.sprites.SpriteBuilder;
import com.pokewords.framework.sprites.components.frames.FrameFactory;
import com.pokewords.framework.views.InputManager;
import com.pokewords.framework.views.SoundPlayer;

public interface IocFactory {

	PrototypeFactory prototypeFactory();

	SoundPlayer soundPlayer();

	UserConfig userConfig();

	InputManager inputManager();

	SpriteBuilder spriteBuilder();

}
