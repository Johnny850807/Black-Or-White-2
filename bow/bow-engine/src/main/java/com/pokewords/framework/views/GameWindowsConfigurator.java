package com.pokewords.framework.views;

import java.awt.*;

/**
 * @author johnny850807 (waterball)
 */
public interface GameWindowsConfigurator {
    GameWindowsConfigurator name(String windowName);

    GameWindowsConfigurator size(int width, int height);

    GameWindowsConfigurator atCenter();

    GameWindowsConfigurator location(int x, int y);

    GameWindowsConfigurator gamePanelBackground(Color color);

    GameWindowDefinition getGameWindowDefinition();

    void apply();

}
