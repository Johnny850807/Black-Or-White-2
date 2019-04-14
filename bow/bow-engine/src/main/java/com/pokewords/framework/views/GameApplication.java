package com.pokewords.framework.views;

import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

import javax.swing.*;
import java.awt.*;

public abstract class GameApplication implements AppView {

	private JPanel gamePanel = new JPanel();
	private JFrame gameFrame = new JFrame();

	private int height = 0, width = 0;
	private int x = 0,y = 0;
	private Color backgroundColor = Color.BLACK;

	/**
	 * Launch the game
	 */
	public void launch() {
		gameInitialSetting();
		gameCustomizedSetting();
	}

	/**
	 * Template method
	 * According to the parameter setting gameView
	 */
	private void gameCustomizedSetting() {
		gamePanel.setBackground(backgroundColor);
		gameFrame.setLocation(x, y);
		gameFrame.setSize(width, height);
		gameFrame.add(gamePanel);
		gameFrame.setVisible(true);
	}

	/**
	 * Template method
	 * Customized setting gameView parameters
	 */
	private final void gameInitialSetting(){
		setFrameHeightAndWidth(height, width);
		setFramebackground(Color.BLACK);
		setFrameLocation(x,y);
	}

	/**
	 * @param height Window height
	 * @param width Window width
	 */
	public void setFrameHeightAndWidth(int height, int width){
		this.height = height;
		this.width = width;
	}

	/**
	 * Window location
	 * @param x Window's x coordinate
	 * @param y Window's y coordinate
	 */
	public void setFrameLocation(int x, int y){
		this.x = x;
		this.y = y;
	}

	/**
	 * @param color GameView background color
	 */
	public void setFramebackground(Color color){
		this.backgroundColor = color;
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
