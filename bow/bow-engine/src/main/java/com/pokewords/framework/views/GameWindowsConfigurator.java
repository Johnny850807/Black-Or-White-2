package com.pokewords.framework.views;

import javax.swing.*;
import java.awt.*;

public class GameWindowsConfigurator {
    private GameFrame gameFrame;

    public GameWindowsConfigurator(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        defaultConfig();
    }

    private void defaultConfig() {
        this.name("Default")
                .size(50, 50)
                .location(0, 0)
                .resizable(true)
                .backgroundColor(Color.black);

        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    //TODO more config methods

    public GameWindowsConfigurator name(String windowName){
        this.gameFrame.setName(windowName);
        return this;
    }

    public GameWindowsConfigurator size(int width, int height){
        this.gameFrame.setSize(width, height);
        return this;
    }

    public GameWindowsConfigurator atCenter() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        return location(dim.width/2 - gameFrame.getSize().width/2,
                dim.height/2 - gameFrame.getSize().height/2);
    }

    public GameWindowsConfigurator location(int x, int y){
        this.gameFrame.setLocation(x, y);
        return this;
    }

    public GameWindowsConfigurator backgroundColor(Color color){
        this.gameFrame.setGamePanelBackground(color);
        return this;
    }

    public GameWindowsConfigurator resizable(boolean resizable) {
        this.gameFrame.setResizable(resizable);
        return this;
    }
}
