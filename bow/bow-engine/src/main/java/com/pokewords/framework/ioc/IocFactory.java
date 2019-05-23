package com.pokewords.framework.ioc;

import com.pokewords.framework.engine.DataSaver;
import com.pokewords.framework.sprites.factories.PrototypeFactory;
import com.pokewords.framework.sprites.factories.SpriteBuilder;
import com.pokewords.framework.sprites.parsing.ScriptParser;
import com.pokewords.framework.sprites.parsing.ScriptRulesParser;
import com.pokewords.framework.views.inputs.InputManager;
import com.pokewords.framework.views.SoundPlayer;

public interface IocFactory {

	PrototypeFactory prototypeFactory();

	SoundPlayer soundPlayer();

	DataSaver userConfig();

	InputManager inputManager();

	SpriteBuilder spriteBuilder();

	ScriptParser scriptParser();

	ScriptRulesParser scriptRulesParser();
}
