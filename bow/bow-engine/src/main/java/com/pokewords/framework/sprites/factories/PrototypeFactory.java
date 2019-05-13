package com.pokewords.framework.sprites.factories;

import com.pokewords.framework.sprites.Sprite;

public interface PrototypeFactory {

	Sprite cloneSprite(String type);

	void addPrototype(String type, Sprite prototype);

	void removePrototype(String type);
}
