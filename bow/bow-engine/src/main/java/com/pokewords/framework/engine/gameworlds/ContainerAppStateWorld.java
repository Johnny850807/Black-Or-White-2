package com.pokewords.framework.engine.gameworlds;

import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.RigidBodyComponent;
import sun.plugin2.main.server.WindowsHelper;

import java.awt.*;

/**
 * A subclass of AppStateWorld which can block the sprites from moving out of the game screen.
 *
 * In this world, there are four additional Sprites created as default, which are topWall, leftWall, rightWall, bottomWall,
 * all of the same type ContainerAppStateWorld.TYPE_WALL. They are arranged to the game screen four-side-margin with a rigid-body.
 * Hence the Sprite will be blocked and a rigidly collision event will happen to it whenever the sprite moves and collides with the margins.
 *
 * If the sprite collides with the top, left, right or bottom margin, the rigid collision between the sprite and topWall, leftWall,
 * rightWall, bottomWall will happen respectively.
 *
 * You can use ContainerAppStateWorld.getTopWall(), ContainerAppStateWorld.getLeftWall(),
 * ContainerAppStateWorld.getRightWall(), ContainerAppStateWorld.getBottomWall() to retrieve those four sprites.
 *
 * Thus, you should treat those four margins as four different sprites of the same type. (This means you can remove any of them.)
 * @author johnny850807 (waterball)
 */
public class ContainerAppStateWorld extends AppStateWorld {
    public static final Object TYPE_WALL = new Object();
    private int wallThickness;
    private int windowWidth;
    private int windowHeight;

    public ContainerAppStateWorld(AppState appState, int windowWidth, int windowHeight) {
        super(appState);
        this.wallThickness = 1000;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    public ContainerAppStateWorld(AppState appState) {
        super(appState);
        this.wallThickness = 1000;
        this.windowWidth = appState.getWindowSize().width;
        this.windowHeight =  appState.getWindowSize().height;
    }

    public ContainerAppStateWorld(AppState appState, int wallThickness) {
        super(appState);
        this.wallThickness = wallThickness;
    }


    private Sprite topWall;
    private Sprite leftWall;
    private Sprite rightWall;
    private Sprite bottomWall;


    @Override
    public void onAppStateCreate() {
        super.onAppStateCreate();
        getGameEngineFacade().declare(TYPE_WALL)
                            .with(RigidBodyComponent.getInstance())
                            .commit();

        topWall = getGameEngineFacade().createSprite(TYPE_WALL);
        topWall.setArea(0, (-1)*wallThickness, windowWidth, wallThickness);

        leftWall = getGameEngineFacade().createSprite(TYPE_WALL);
        leftWall.setArea((-1)*wallThickness, 0, wallThickness, windowHeight);

        rightWall = getGameEngineFacade().createSprite(TYPE_WALL);
        rightWall.setArea(windowWidth, 0, wallThickness, windowHeight);

        bottomWall = getGameEngineFacade().createSprite(TYPE_WALL);
        bottomWall.setArea(0, windowHeight, windowWidth, wallThickness);

        spawn(topWall);
        spawn(leftWall);
        spawn(rightWall);
        spawn(bottomWall);
    }

    public Sprite getTopWall() {
        return topWall;
    }

    public Sprite getLeftWall() {
        return leftWall;
    }

    public Sprite getRightWall() {
        return rightWall;
    }

    public Sprite getBottomWall() {
        return bottomWall;
    }
}
