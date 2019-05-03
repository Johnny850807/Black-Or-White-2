package com.pokewords.framework.sprites.components;


import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;
import com.pokewords.framework.views.Canvas;

import java.awt.Image;


public class TextureFrame implements Frame {
	private Sprite sprite;
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
	public void apply(AppStateWorld gameWorld, Sprite sprite) {

	}

	@Override
	public void addEffect(GameEffect effect) {

	}

	@Override
	public void renderItself(Canvas canvas) {
		canvas.renderText(sprite.getX(), sprite.getY(), image);
	}

}
