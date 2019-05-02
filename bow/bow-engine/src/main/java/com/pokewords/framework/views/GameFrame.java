package com.pokewords.framework.views;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame implements AppView{
    private GamePanel gamePanel = new GamePanel();

    @Override
    public void onAppInit() {
        setContentPane(gamePanel);
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

    public void setBackground(Color backgroundColor) {
        gamePanel.setBackground(backgroundColor);
    }
}
