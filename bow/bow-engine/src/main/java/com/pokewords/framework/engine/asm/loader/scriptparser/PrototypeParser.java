package com.pokewords.framework.engine.asm.loader.scriptparser;

import com.pokewords.framework.sprites.PrototypeFactory;

public interface PrototypeParser {

	void parse(String script);

	PrototypeFactory get();

}
