package com.pokewords.framework.sprites.components.gameworlds;

import com.pokewords.framework.engine.exceptions.GameEngineException;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.AppStateLifeCycleListener;
import com.pokewords.framework.views.RenderedLayers;

import java.util.List;

public class AppStateWorld implements AppStateLifeCycleListener {

    private List<Sprite> sprites;
    private List<CollisionHandler> collisionHandlers;

    public void spawn(Sprite sprite) {
        //TODO
    }

    public RenderedLayers getRenderedLayers() {
        return null;//TODO
    }


    public void addCollisionHandler(CollisionHandler collisionHandler) {
        //TODO
    }

    public void removeCollisionHandler(CollisionHandler collisionHandler) {
        //TODO
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    public List<CollisionHandler> getCollisionHandlers() {
        return collisionHandlers;
    }

    public void setSprites(List<Sprite> sprites) {
        this.sprites = sprites;
    }

    @Override
    public void onAppStateStart(AppStateWorld world) {
        if (world != this)
            throw new GameEngineException("The world is not consistent from triggering the onAppStateStart() method from the AppState");
        //TODO
    }

    @Override
    public void onAppStateEnter() {
        //TODO
    }

    @Override
    public void onAppStateExit() {
        //TODO
    }

    @Override
    public void onAppStateDestroy() {
        //TODO
    }

    @Override
    public void onUpdate(double tpf) {
        //TODO
    }
}
