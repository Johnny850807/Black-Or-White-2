package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;
import com.pokewords.framework.views.Canvas;

public interface Frame {

	void apply(AppStateWorld gameWorld);

	void renderItself(Canvas canvas);

}
