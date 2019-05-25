package com.pokewords.framework.engine.gameworlds;

import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.RigidBodyComponent;

import java.awt.*;

public class ContainerAppStateWorld extends AppStateWorld {

    public ContainerAppStateWorld(AppState appState, GameEngineFacade gameEngineFacade) {
        super(appState, gameEngineFacade);
    }

    Rectangle gameArea = new Rectangle();


    @Override
    public void handleSpriteRigidCollisionDetection(Sprite sprite, Runnable rigidCollisionTrigger) {
        super.handleSpriteRigidCollisionDetection(sprite, rigidCollisionTrigger);

        Dimension windowsSize = gameEngineFacade.getGameWindowDefinition().size;
        gameArea.setSize(windowsSize);
        for (Sprite s : getSprites()) {
            if (s.hasComponent(RigidBodyComponent.class))
            {
                while (!gameArea.contains(s.getBody()))
                    rigidCollisionTrigger.run();
            }
        }
    }
}
