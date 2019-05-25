package com.pokewords.framework.ioc;

import com.pokewords.framework.engine.DataSaver;
import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.sprites.factories.PrototypeFactory;
import com.pokewords.framework.sprites.factories.SpriteBuilder;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.AppView;
import com.pokewords.framework.views.helpers.galleries.GalleryFactory;
import com.pokewords.framework.views.inputs.InputManager;
import com.pokewords.framework.views.SoundPlayer;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;

public interface IocContainer {

	PrototypeFactory prototypeFactory();

	SoundPlayer soundPlayer();

	DataSaver userConfig();

	InputManager inputManager();

	SpriteBuilder spriteBuilder();

	GalleryFactory galleryFactory();

	SpriteInitializer spriteInitializer();

}
