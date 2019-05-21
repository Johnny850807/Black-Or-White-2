package com.pokewords.framework.views;

import java.awt.*;

public interface Canvas {
	void renderImage(int x, int y, Image image);
	void renderImage(int x, int y, int width, int height, Image image);

	default void renderImageWithCenterAdjusted(int x, int y, int width, int height, Image image) {
		renderImage(x-width/2, y-height/2, width, height, image);
	}

	Dimension renderText(int x, int y, String text, Color color, Font font);
	void renderImageWithCenterAdjusted(int x, int y, Image image);
	Dimension renderTextWithCenterAdjusted(int x, int y, String text, Color color, Font font);

	void renderRectangle(Rectangle rectangle, Color color);
}
