package com.pokewords.framework.views;

public interface AppView {

	void onAppInit();

	void onAppLoading();

	void onAppStarted();

	void onRender(RenderedLayers renderedLayers);

}
