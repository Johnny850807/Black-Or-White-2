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
        pack();
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

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public int getTitleBarHeight() {
        return getInsets().top;
    }
}
