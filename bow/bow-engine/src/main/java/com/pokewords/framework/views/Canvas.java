package com.pokewords.framework.views;

import com.pokewords.framework.sprites.components.frames.ImageFrame;
import com.pokewords.framework.sprites.components.frames.RectangleFrame;
import com.pokewords.framework.sprites.components.frames.RoundedRectangleFrame;
import com.pokewords.framework.sprites.components.frames.StringFrame;

import java.awt.*;

/**
 * Canvas for rendering different type of Frame, which applies visitor pattern.
 * @author johnny850807 (waterball)
 */
public interface Canvas {
	Dimension render(StringFrame stringFrame);
	void render(ImageFrame imageFrame);
	void render(RectangleFrame rectangleFrame);
    void render(RoundedRectangleFrame roundedRectangleFrame);
}
