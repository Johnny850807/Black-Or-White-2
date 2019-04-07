package com.pokewords.framework.sprites;

public interface PrototypeFactory {

	Sprite cloneSprite(String type);

	void addPrototype(String type, Sprite prototype);

	void removePrototype(String type);
}
