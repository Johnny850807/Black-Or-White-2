package com.pokewords.framework.sprites.components;


import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;
import com.pokewords.framework.views.Canvas;

import java.awt.Image;


public class TextureFrame extends DefaultFrame {
	private Sprite sprite;  //TODO how to inject the sprite?
	private Image image;
	private int layerIndex;

	public TextureFrame(Image image, int layerIndex) {
		this.image = image;
		this.layerIndex = layerIndex;
	}

	@Override
	public int getLayerIndex() {
		return layerIndex;
	}

	@Override
	public void renderItself(Canvas canvas) {
		canvas.renderImage(sprite.getX(), sprite.getY(), image);
	}

}
