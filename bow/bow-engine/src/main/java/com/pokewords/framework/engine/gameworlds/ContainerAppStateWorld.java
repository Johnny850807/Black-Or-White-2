package com.pokewords.framework.engine.gameworlds;

import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.RigidBodyComponent;

import java.awt.*;

/**
 * A subclass of AppStateWorld which can block the sprites from moving out of the game panel.
 * @author johnny850807 (waterball)
 */
public class ContainerAppStateWorld extends AppStateWorld {

    public ContainerAppStateWorld(AppState appState) {
        super(appState);
    }

    Rectangle gameArea = new Rectangle(0, 0, 0, 0);

    @Override
    public void onSpritePositionChanged(Sprite sprite) {
        super.onSpritePositionChanged(sprite);

        Dimension windowsSize = getGameEngineFacade().getGameWindowDefinition().size;
        gameArea.setSize(windowsSize);

        if (sprite.hasComponent(RigidBodyComponent.class)
             && !gameArea.contains(sprite.getBody()))
                sprite.resumeToLatestPosition();
    }

}
