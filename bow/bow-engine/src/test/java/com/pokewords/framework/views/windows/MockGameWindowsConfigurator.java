package com.pokewords.framework.views.windows;

import java.awt.*;

/**
 * @author johnny850807 (waterball)
 */
public class MockGameWindowsConfigurator implements GameWindowsConfigurator {
    private GameWindowDefinition gameWindowDefinition = new GameWindowDefinition();

    @Override
    public GameWindowsConfigurator name(String windowName) {
        gameWindowDefinition.name = windowName;
        return this;
    }

    @Override
    public GameWindowsConfigurator size(int width, int height) {
        gameWindowDefinition.size = new Dimension(width, height);
        return this;
    }


    @Override
    public GameWindowsConfigurator location(int x, int y) {
        gameWindowDefinition.location = new Point(x, y);
        return this;
    }

    @Override
    public GameWindowsConfigurator atCenter() {
        gameWindowDefinition.location = gameWindowDefinition.center();
        return this;
    }

    @Override
    public GameWindowsConfigurator gamePanelBackground(Color color) {
        gameWindowDefinition.gamePanelBackground = color;
        return this;
    }

    @Override
    public GameWindowDefinition getGameWindowDefinition() {
        return gameWindowDefinition;
    }
}