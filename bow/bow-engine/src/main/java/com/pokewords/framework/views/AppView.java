package com.pokewords.framework.views;

import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

public interface AppView {

	void onAppInit();

	void onAppLoading();

	void onAppStarted();

	void onRender(RenderedLayers renderedLayers);

	AppStateWorld getWorld();

}
