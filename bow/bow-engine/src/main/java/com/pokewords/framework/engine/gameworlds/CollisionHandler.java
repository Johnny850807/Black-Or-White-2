package com.pokewords.framework.engine.gameworlds;

import com.pokewords.framework.commons.Pair;
import com.pokewords.framework.sprites.Sprite;


public abstract class CollisionHandler {
	private Type type;

	public CollisionHandler(Object s1Type, Object s2Type) {
		type = new Type(s1Type, s2Type);
	}

	public abstract void onCollision(Sprite s1, Sprite s2);


	public Object getFirstType() {
		return type.getFirst();
	}

	public Object getSecondType() {
		return type.getSecond();
	}

	public static class Type extends Pair {
		@SuppressWarnings("unchecked")
		public Type(Object first, Object second) {
			super(first, second);
		}
	}
}
