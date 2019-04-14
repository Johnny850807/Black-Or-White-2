package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;
import com.pokewords.framework.views.Canvas;

import java.util.function.Consumer;

public interface Frame extends Cloneable{

	/**
	 * Actually effect the AppStateWorld from all of the effects added by addEffect(Consumer<AppStateWorld>) method.
	 * @param gameWorld the effected AppStateWorld
	 */
	void apply(AppStateWorld gameWorld);

	/**
	 * Add an effect in a form of functional interface consuming the effected AppStateWorld.
	 * All the effects added by this method will be applied during the invocation of apply(AppStateWorld) method.
	 * @param effectToWorld functional interface describe how the effect should apply to the given world
	 */
	void addEffect(Consumer<AppStateWorld> effectToWorld);

	/**
	 * Render the frame itself on the canvas.
	 * @param canvas The canvas which can be rendered some images.
	 */
	void renderItself(Canvas canvas);

}
