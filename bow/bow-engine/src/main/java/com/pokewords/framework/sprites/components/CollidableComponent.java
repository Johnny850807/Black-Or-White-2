package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;

/**
 * @author johnny850807
 */
public class CollidableComponent implements Component {

	@Override
	public void onBoundToSprite(Sprite sprite) {}

	@Override
	public void onStart() {}

	@Override
	public void onUpdate() {}

	@Override
	public CollidableComponent clone() {
		try {
			return (CollidableComponent) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

}
