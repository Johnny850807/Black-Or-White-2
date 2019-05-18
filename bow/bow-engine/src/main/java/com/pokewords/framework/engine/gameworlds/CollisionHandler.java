package com.pokewords.framework.engine.gameworlds;

import com.pokewords.framework.sprites.Sprite;


public abstract class CollisionHandler {
	public Object s1Type;
	public Object s2Type;

	public CollisionHandler(Object s1Type, Object s2Type) {
		this(s1Type.toString(), s2Type.toString());
	}

	public CollisionHandler(String s1Type, String s2Type) {
		this.s1Type = s1Type;
		this.s2Type = s2Type;
	}

	public abstract void onCollision(Sprite s1, Sprite s2);
}
