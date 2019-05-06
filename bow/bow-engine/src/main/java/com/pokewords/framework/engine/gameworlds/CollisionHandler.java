package com.pokewords.framework.engine.gameworlds;

import com.pokewords.framework.sprites.Sprite;


@FunctionalInterface
public interface CollisionHandler {
	void onCollision(Sprite s1, Sprite s2);
}
