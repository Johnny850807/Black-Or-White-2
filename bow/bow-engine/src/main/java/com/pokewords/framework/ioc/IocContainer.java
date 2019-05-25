package com.pokewords.framework.ioc;

import com.pokewords.framework.engine.DataSaver;
import com.pokewords.framework.sprites.factories.PrototypeFactory;
import com.pokewords.framework.sprites.factories.SpriteBuilder;
import com.pokewords.framework.views.helpers.galleries.GalleryFactory;
import com.pokewords.framework.views.inputs.InputManager;
import com.pokewords.framework.views.SoundPlayer;

public interface IocContainer {

	PrototypeFactory prototypeFactory();

	SoundPlayer soundPlayer();

	DataSaver userConfig();

	InputManager inputManager();

	SpriteBuilder spriteBuilder();

	GalleryFactory galleryFactory();
}
