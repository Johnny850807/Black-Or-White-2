package com.pokewords.framework.views.windows;

import java.awt.*;

/**
 * @author johnny850807 (waterball)
 */
public interface GameWindowsConfigurator {
    GameWindowsConfigurator name(String windowName);

    default GameWindowsConfigurator gameSize(Dimension dimension) {
        return gameSize(dimension.width, dimension.height);
    }

    GameWindowsConfigurator gameSize(int width, int height);

    GameWindowsConfigurator atCenter();

    GameWindowsConfigurator location(int x, int y);

    GameWindowsConfigurator gamePanelBackground(Color color);

    GameWindowDefinition getGameWindowDefinition();

}
