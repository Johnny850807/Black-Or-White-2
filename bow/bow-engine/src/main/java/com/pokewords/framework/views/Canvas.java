package com.pokewords.framework.views;

import java.awt.*;

public interface Canvas {

	void renderText(int x, int y, Image image);
	void renderText(int x, int y, String text);
}
