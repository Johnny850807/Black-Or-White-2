package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.views.Canvas;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public interface Frame extends Cloneable {

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
	 * If the frame is added to some component, and that component is of some sprite.
	 * Then this method will be invoked with that Sprite passed.
	 * Furthermore, the frame's area will be determined by its Sprite owner's area, which are (x, y, width, height).
	 * In this situation, the invocations of the setters of (position, size) will throw an IllegalStateException.
	 * Hence, you should set the sprite's position and size rather than frame's.
	 *
	 * If the frame is not owned (or namely bound toby ) any Sprite, then it's fine to just
	 * set its position and dimension.
	 * @param sprite owner of the frame
	 */
	void boundToSprite(@Nullable Sprite sprite);

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

	/**
	 * @return get the sprite bound to the frame, return null if the frame is not bound to any sprite
	 */
	@Nullable Sprite getSprite();

    void setPosition(Point position) throws IllegalStateException;

	void setSize(Dimension dimension) throws IllegalStateException;

	/**
	 * @return get the x position of the frame, if the frame is bound to a Sprite, then this will return sprite's x
	 */
	int getX();

	/**
	 * @return get the y position of the frame, if the frame is bound to a Sprite, then it will return sprite's y
	 */
	int getY();

	/**
	 * @return get the width of the frame, if the frame is bound to a Sprite, then it will return sprite's width
	 */
	int getWidth();

	/**
	 * @return get the height of the frame, if the frame is bound to a Sprite, then it will return sprite's height
	 */
	int getHeight();


	Frame clone();
}
