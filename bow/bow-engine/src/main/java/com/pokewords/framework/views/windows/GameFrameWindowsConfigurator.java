package com.pokewords.framework.views.windows;

import javax.swing.*;
import java.awt.*;

/**
 * @author johnny850807 (waterball)
 */
public class GameFrameWindowsConfigurator implements GameWindowsConfigurator {
    private GameFrame gameFrame;
    private GameWindowDefinition gameWindowDefinition = new GameWindowDefinition();

    public GameFrameWindowsConfigurator(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        defaultConfig();
    }

    private void defaultConfig() {
        this.name("Default")
                .gameSize(50, 50)
                .location(0, 0)
                .gamePanelBackground(Color.black);

        gameFrame.setResizable(false);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    @Override
    public GameWindowsConfigurator name(String windowName){
        gameWindowDefinition.name = windowName;
        gameFrame.setTitle(windowName);
        return this;
    }

    @Override
    public GameWindowsConfigurator gameSize(int width, int height){
        gameWindowDefinition.size = new Dimension(width, height);
        gameFrame.getGamePanel().setPreferredSize(new Dimension(width, height));
        gameFrame.pack();
        return this;
    }

    @Override
    public GameWindowsConfigurator atCenter() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        return location(dim.width/2 - gameFrame.getSize().width/2,
                dim.height/2 - gameFrame.getSize().height/2);
    }

    @Override
    public GameWindowsConfigurator location(int x, int y){
        gameWindowDefinition.location = new Point(x, y);
        gameFrame.setLocation(x, y);
        return this;
    }

    @Override
    public GameWindowsConfigurator gamePanelBackground(Color color){
        gameWindowDefinition.gamePanelBackground = color;
        gameFrame.setGamePanelBackground(color);
        return this;
    }


    @Override
    public GameWindowDefinition getGameWindowDefinition() {
        return gameWindowDefinition;
    }
}
