package com.pokewords.framework.sprites.components.frames;


import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.views.Canvas;

import java.awt.Image;


public class TextureEffectFrame extends DefaultEffectFrame {
	private Sprite sprite;
	private Image image;
	private int layerIndex;

	public TextureEffectFrame(Image image, int layerIndex) {
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
