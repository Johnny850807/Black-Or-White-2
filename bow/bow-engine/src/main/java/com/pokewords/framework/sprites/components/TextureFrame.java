package com.pokewords.framework.sprites.components;


import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;
import com.pokewords.framework.views.Canvas;

import java.awt.Image;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class TextureFrame implements Frame {
	private Image image;

	public TextureFrame(Image image) {
		this.image = image;
	}

	@Override
	public void apply(AppStateWorld gameWorld, Sprite sprite) {

	}

	@Override
	public void addEffect(GameEffect effect) {

	}

	public void renderItself(Canvas canvas) {

	}

}
