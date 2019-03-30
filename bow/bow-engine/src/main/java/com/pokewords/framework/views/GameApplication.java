package com.pokewords.framework.views;

import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

public abstract class GameApplication implements AppView {

	public void launch() {

	}


	/**
	 * @see com.pokewords.framework.views.AppView#onAppInit()
	 * 
	 *  
	 */
	public void onAppInit() {

	}


	/**
	 * @see com.pokewords.framework.views.AppView#onAppLoading()
	 * 
	 *  
	 */
	public void onAppLoading() {

	}


	/**
	 * @see com.pokewords.framework.views.AppView#onAppStarted()
	 * 
	 *  
	 */
	public void onAppStarted() {

	}


	/**
	 * @see com.pokewords.framework.views.AppView #onRender(framework.views.RenderedLayers)
	 * 
	 *  
	 */
	public void onRender(RenderedLayers renderedLayers) {

	}


	/**
	 * @see com.pokewords.framework.views.AppView #getWorld()
	 */
	public AppStateWorld getWorld() {
		return null;
	}

}
