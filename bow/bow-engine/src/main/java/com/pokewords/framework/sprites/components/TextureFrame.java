package com.pokewords.framework.sprites.components;


import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;
import com.pokewords.framework.views.Canvas;

import java.awt.Image;
import java.util.function.Consumer;


public class TextureFrame implements Frame {

	private Image image;


	public TextureFrame(Image image) {
		this.image = image;
	}

	/**
	 * @see com.pokewords.framework.sprites.components.Frame #apply(framework.sprites.components.gameworlds.AppStateWorld)
	 * 
	 *  
	 */
	public void apply(AppStateWorld gameWorld) {

	}

	@Override
	public void addEffect(Consumer<AppStateWorld> effectToWorld) {

	}


	/**
	 * @see com.pokewords.framework.sprites.components.Frame #renderItself(framework.sprites.components.Canvas)
	 * 
	 *  
	 */
	public void renderItself(Canvas canvas) {

	}

}
