package com.pokewords.framework.views;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame implements AppView{
    private GamePanel gamePanel;

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

}
