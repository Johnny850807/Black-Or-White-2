package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.views.Canvas;
import org.jetbrains.annotations.Nullable;

public interface Frame extends Cloneable {
	/**
	 * @return get the id of frame's
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
	 * @return owner of the frame
	 */
	@Nullable Sprite getSprite();


	Frame clone();
}
