package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.views.Canvas;
import org.jetbrains.annotations.Nullable;

public interface Frame extends Cloneable {
	/**
	 * @return get the event of frame's
	 */
	int getId();

	/**
	 * @return get the layer index
	 */
	int getLayerIndex();

	/**
	 * @param layerIndex the layer index
	 */
	void setLayerIndex(int layerIndex);


	/**
	 * Render the frame itself on the canvas.
	 * @param canvas The canvas which can be rendered some images.
	 */
	void renderItself(Canvas canvas);


	/**
	 * @param sprite owner of the frame
	 */
	void setSprite(@Nullable Sprite sprite);

	/**
	 * @return the flags config for the frame
	 */
	int getFlags();

	/**
	 * set the flags, this will replace the previous setting
	 */
	void setFlags(int flags);

	/**
	 * @param flag a constant flags
	 * @return if the frame has been config for this flags
	 */
	default boolean hasFlag(int flag) {
		int and = getFlags() & flag;
		return and != 0;
	}


	default void assertSpriteNonNull() {
		if (getSprite() == null)
			throw new IllegalStateException("The sprite is null, so the method is not supposed to use now.");
	}


	/**
	 * @return owner of the frame
	 */
	@Nullable Sprite getSprite();

	@SuppressWarnings("ConstantConditions")
	default int getX() {
		assertSpriteNonNull();
		return getSprite().getX();
	}

	@SuppressWarnings("ConstantConditions")
	default int getY() {
		assertSpriteNonNull();
		return getSprite().getY();
	}

	@SuppressWarnings("ConstantConditions")
	default int getWidth() {
		assertSpriteNonNull();
		return getSprite().getWidth();
	}

	@SuppressWarnings("ConstantConditions")
	default int getHeight() {
		assertSpriteNonNull();
		return getSprite().getHeight();
	}


	Frame clone();
}
