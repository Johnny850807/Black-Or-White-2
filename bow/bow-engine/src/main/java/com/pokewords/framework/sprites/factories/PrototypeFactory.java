package com.pokewords.framework.sprites.factories;

import com.pokewords.framework.sprites.Sprite;

public interface PrototypeFactory {

	Sprite cloneSprite(Object type);

	void addPrototype(Object type, Sprite prototype);

	void removePrototype(Object type);
}
