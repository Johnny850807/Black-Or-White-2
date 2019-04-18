package com.pokewords.framework.views;

import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

import javax.swing.*;
import java.awt.*;

/**
 * @author shawn
 *
 */
public abstract class GameApplication implements AppView {

	/**
	 * Initial parameter
	 */
	private int height = 200, width = 200;
	private int x = 0,y = 0;
	private Color backgroundColor = Color.BLACK;
	private String windowName = "Game engine";
	private JPanel gamePanel;
	private JFrame gameFrame;


	public void launch() {
		gameCustomizedSetting();
	}

	/**
	 * Template method
	 * According to the parameter setting gameView
	 */
	private void gameCustomizedSetting() {
		gamePanel = new JPanel();
		gameFrame = new JFrame(windowName);
		gamePanel.setBackground(backgroundColor);
		gameFrame.setLocation(x,y);
		gameFrame.setSize(width,height);
		gameFrame.add(gamePanel);
		gameFrame.setName(windowName);
		gameFrame.setVisible(true);
	}

	/**
	 * @param windowName window name
	 */
	public void setWindowName(String windowName){
		this.gameFrame.setName(windowName);
	}

	/**
	 * @param height Window height
	 * @param width Window width
	 */
	public void setWindowHeightAndWidth(int height, int width){
		this.height = height;
		this.width = width;
	}

	/**
	 * Window location
	 * @param x Window's x coordinate
	 * @param y Window's y coordinate
	 */
	public void setWindowLocation(int x, int y){
		this.x = x;
		this.y = y;
	}

	/**
	 * @param color GameView background color
	 */
	public void setWindowbackgroundColor(Color color){
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
