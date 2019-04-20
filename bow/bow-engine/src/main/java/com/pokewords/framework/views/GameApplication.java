package com.pokewords.framework.views;

import javax.swing.*;
import java.awt.*;
import com.pokewords.framework.sprites.components.Frame;

/**
 * @author shawn
 *
 */
public abstract class GameApplication implements AppView {

	/**
	 * Initial parameter
	 */
	private int height = 200, width = 200;
	private int x = 0, y = 0;
	private Color backgroundColor = Color.BLACK;
	private String windowName = "Game engine";
	private JPanel gamePanel = new JPanel();
	private JFrame gameFrame = new JFrame(windowName);

	public void launch() {
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
		gameFrame.setTitle(windowName);
		gameFrame.setVisible(true);
	}

	/**
	 * @param windowName window name
	 */
	public void setWindowName(String windowName){
		this.windowName = windowName;
	}

	/**
	 * @param width Window width
	 * @param height Window height
	 */
	public void setWindowSize(int width, int height){
		this.width = width;
		this.height = height;
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

	@Override
	public void onRender(RenderedLayers renderedLayers) {
		gamePanel = new GamePanel(renderedLayers);
	}

	private class GamePanel extends JPanel {
		private RenderedLayers renderedLayers;

		public GamePanel(RenderedLayers renderedLayers){
			this.renderedLayers = renderedLayers;
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			for(int i = 0; i < renderedLayers.layers.size(); i++) {
				for(int j = 0; j < renderedLayers.layers.size(); j++) {
					Frame frames = renderedLayers.layers.get(i).get(j);
					frames.renderItself(GraphicsCanvas.of(g));
				}
			}
		}
	}

}
