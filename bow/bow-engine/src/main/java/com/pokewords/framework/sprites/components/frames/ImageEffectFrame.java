package com.pokewords.framework.sprites.components.frames;


import com.pokewords.framework.views.Canvas;

import java.awt.*;
import java.util.Objects;


public class ImageEffectFrame extends DefaultEffectFrame {
	private int duration;
	private Image image;

	public ImageEffectFrame(int id, int layerIndex, int duration, Image image) {
		super(id, layerIndex);
		this.duration = duration;
		this.image = image;
	}

	@Override
	public int getDuration() {
		return duration;
	}


	@Override
	public void renderItself(Canvas canvas) {
		assert sprite != null;
		canvas.renderImage(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), image);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		ImageEffectFrame that = (ImageEffectFrame) o;
		return duration == that.duration &&
				image.equals(that.image);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), duration, image);
	}
}
