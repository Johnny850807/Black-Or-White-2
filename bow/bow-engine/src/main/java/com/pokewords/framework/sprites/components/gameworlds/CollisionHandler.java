package com.pokewords.framework.sprites.components.gameworlds;

import com.pokewords.framework.sprites.Sprite;

public abstract class CollisionHandler {
	private Sprite type1;
	private Sprite type2;

	public abstract void onCollision(Sprite s1, Sprite s2);


}
