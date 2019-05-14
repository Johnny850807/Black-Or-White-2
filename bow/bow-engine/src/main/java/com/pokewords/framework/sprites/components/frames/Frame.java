package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.views.Canvas;

public interface Frame extends Cloneable {
	/**
	 * @return get the layer index  (z-index)
	 */
	int getLayerIndex();


	/**
	 * Render the frame itself on the canvas.
	 * @param canvas The canvas which can be rendered some images.
	 */
	void renderItself(Canvas canvas);

}
