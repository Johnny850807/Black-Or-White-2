package com.pokewords.framework.views;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import com.pokewords.framework.sprites.components.Frame;

/**
 * @author shawn
 *
 */
public abstract class GameApplication implements AppView {

	/**
	 * The default value of the length and width of the window
	 */
	private int height = 200, width = 200;

	/**
	 * The default value of the window at the position of the screen
	 */
	private int x = 0, y = 0;

	/**
	 * The default value of the window background color
	 */
	private Color backgroundColor = Color.BLACK;

	/**
	 * The default value of the window name.
	 */
	private String windowName = "Game engine";

	private GamePanel gamePanel = new GamePanel();

	private JFrame gameFrame = new JFrame(windowName);

	/**
	 * Launch the game window
	 */
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

	/**
	 * Repaint the gamePanel with the frame inside the RenderedLayers.
	 * @param renderedLayers new renderedLayers.
	 */
	@Override
	public void onRender(RenderedLayers renderedLayers) {
		gamePanel.setRenderedLayers(renderedLayers);
		gamePanel.repaint();
	}


	private class GamePanel extends JPanel {
		private RenderedLayers renderedLayers;

		/**
		 * set the new renderedLayers.
		 * @param renderedLayers new renderedLayers.
		 */
		private void setRenderedLayers(RenderedLayers renderedLayers){
			this.renderedLayers = renderedLayers;
		}

		/**
		 * Repaint the frame in each renderedLayers
		 * @param g Basic graph
		 */
		@Override
		public void paint(Graphics g) {
			super.paint(g);
            List<List<Frame>> layers = renderedLayers.layers;
            for (List<Frame> layer : layers) {
                for (Frame frames : layer) {
                    frames.renderItself(GraphicsCanvas.of(g));
                }
            }
		}
	}

}
