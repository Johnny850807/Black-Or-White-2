package com.pokewords.framework.views.windows;

import com.pokewords.framework.views.AppView;
import com.pokewords.framework.views.RenderedLayers;
import com.pokewords.framework.views.inputs.InputManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author johnny850807 (waterball)
 */
public class GameFrame extends JFrame implements AppView {
    private GamePanel gamePanel;
    private GameWindowDefinition gameWindowDefinition;
    private InputManager inputManager;

    public GameFrame(GamePanel gamePanel, InputManager inputManager) throws HeadlessException {
        this.gamePanel = gamePanel;
        this.inputManager = inputManager;
        setContentPane(gamePanel);
        addKeyListener(new GameFrame.KeyListener());
        addMouseListener(new GameFrame.MouseListener());
    }
    private class KeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            inputManager.onButtonPressedDown(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            inputManager.onButtonReleasedUp(e.getKeyCode());
        }
    }


    private class MouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            inputManager.onMouseHitDown(e.getPoint());
        }


        @Override
        public void mouseReleased(MouseEvent e) {
            inputManager.onMouseReleasedUp(e.getPoint());
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            inputManager.onMouseMoved(e.getPoint());
        }
    }
    @Override
    public void onAppInit() {
        gamePanel.onAppInit();
        setVisible(true);
    }

    @Override
    public void onAppLoading() {
        gamePanel.onAppLoading();
    }

    @Override
    public void onAppStarted() {
        gamePanel.onAppStarted();
    }

    @Override
    public void onRender(RenderedLayers renderedLayers) {
        gamePanel.onRender(renderedLayers);
    }

    public void setGamePanelBackground(Color gamePanelBackground) {
        gamePanel.setGamePanelBackground(gamePanelBackground);
    }

    public GameWindowDefinition getGameWindowDefinition() {
        return gameWindowDefinition;
    }
}
