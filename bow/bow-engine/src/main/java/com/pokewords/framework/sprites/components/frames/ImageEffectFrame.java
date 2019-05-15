package com.pokewords.framework.sprites.components.frames;


import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.views.Canvas;

import java.awt.Image;


public class ImageEffectFrame extends DefaultEffectFrame {
	private Sprite sprite;
	private int id;
	private int layerIndex;
	private int duration;
	private Image image;

	public ImageEffectFrame(int id, int layerIndex, int duration, Image image) {
		this.id = id;
		this.layerIndex = layerIndex;
		this.duration = duration;
		this.image = image;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public int getLayerIndex() {
		return layerIndex;
	}

	@Override
	public int getDuration() {
		return duration;
	}

	@Override
	public void renderItself(Canvas canvas) {
		canvas.renderImage(sprite.getX(), sprite.getY(), image);
	}

}
