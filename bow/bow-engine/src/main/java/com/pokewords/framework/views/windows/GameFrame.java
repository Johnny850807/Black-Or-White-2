package com.pokewords.framework.views.windows;

import com.pokewords.framework.views.AppView;
import com.pokewords.framework.views.RenderedLayers;

import javax.swing.*;
import java.awt.*;

/**
 * @author johnny850807 (waterball)
 */
public class GameFrame extends JFrame implements AppView {
    private GamePanel gamePanel;
    private GameWindowDefinition gameWindowDefinition;

    public GameFrame(GamePanel gamePanel) throws HeadlessException {
        this.gamePanel = gamePanel;
        setContentPane(gamePanel);
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
