package com.pokewords.framework.views;

import java.awt.*;

public interface Canvas {

	void renderImage(int x, int y, Image image);
	void renderText(int x, int y, String text, Color color, Font font);
	void renderImageByCenter(int x, int y, Image image);
	void renderTextByCenter(int x, int y, String text, Color color, Font font);
}
