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
    public static final Object TYPE_WALL = new Object();
    private Sprite topWall;
    private Sprite leftWall;
    private Sprite rightWall;
    private Sprite bottomWall;

    public ContainerAppStateWorld(AppState appState) {
        super(appState);
    }

    Rectangle gameArea = new Rectangle(0, 0, 0, 0);

    @Override
    public void onAppStateCreate() {
        super.onAppStateCreate();
        getGameEngineFacade().declare(TYPE_WALL)
                            .with(RigidBodyComponent.getInstance())
                            .commit();

        Dimension windowsSize = getGameEngineFacade().getGameWindowDefinition().size;
        topWall = getGameEngineFacade().createSprite(TYPE_WALL);
        topWall.setArea(0, -1, windowsSize.width, 1);

        leftWall = getGameEngineFacade().createSprite(TYPE_WALL);
        leftWall.setArea(-1, 0, 1, windowsSize.height);

        rightWall = getGameEngineFacade().createSprite(TYPE_WALL);
        rightWall.setArea(windowsSize.width, 0, 1, windowsSize.height);

        bottomWall = getGameEngineFacade().createSprite(TYPE_WALL);
        bottomWall.setArea(0, windowsSize.height, windowsSize.width, 1);

        spawn(topWall);
        spawn(leftWall);
        spawn(rightWall);
        spawn(bottomWall);
    }


}
