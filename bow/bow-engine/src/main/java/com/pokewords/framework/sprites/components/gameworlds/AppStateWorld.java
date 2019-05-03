package com.pokewords.framework.sprites.components.gameworlds;

import com.pokewords.framework.engine.exceptions.GameEngineException;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.AppStateLifeCycleListener;
import com.pokewords.framework.views.RenderedLayers;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class AppStateWorld implements AppStateLifeCycleListener {

    private List<Sprite> sprites;
    private List<CollisionHandler> collisionHandlers;

    /**
     * @param sprite the spawned sprite to be added into the world
     * @return the Sprite's unique id
     */
    public int spawn(Sprite sprite) {
        //TODO
        return 0;
    }

    /**
     * @param sprite the spawned sprite to be added into the world
     * @param time the time-delay to spawn
     * @param timeUnit the time unit of
     * @param callback the callback receives the spawned sprite's id when the sprite is actually spawned
     */
    public void spawnDelay(Sprite sprite, int time, TimeUnit timeUnit, @Nullable Consumer<Integer> callback) {
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
    public void onUpdate(double timePerFrame) {
        //TODO
    }

    /**
     * @return if the sprite is in the world
     */
    public boolean contains(Sprite sprite) {
        return true;
    }

    /**
     * @return if the sprite who owns the id is in the world
     */
    public boolean contains(int spriteId) {
        return true;
    }

    /**
     * @return the sprite's id
     */
    public int getId(Sprite sprite) {
        return 0;
    }

    /**
     * @return the sprite who owns the id
     */
    public Sprite getSprite(int spriteId) {
        return null;
    }

    /**
     * @return the sprites within the area (x, y, w, h)
     */
    public Set<Sprite> getSpritesWithinArea(int x, int y, int w, int h) {
        return null;
    }

    /**
     * @return the sprites within the area (x, y, w, h)
     */
    public Set<Sprite> getSpritesWithinArea(Rectangle area) {
        return this.getSpritesWithinArea(area.x, area.y, area.width, area.height);
    }


    /**
     * @return the sprites within the area (x, y, w, h) from the center point of the given sprite
     */
    public Set<Sprite> getSpritesWithinArea(Sprite sprite, int w, int h) {
        return null;
    }

    /**
     * @return the sprites within the area (x, y, w, h) from the center point of the given sprite
     */
    public Set<Sprite> getSpritesWithinArea(Sprite sprite, Dimension dimension) {
        return this.getSpritesWithinArea(sprite, dimension.width, dimension.height);
    }

    /**
     * @return the sprites collided with the given sprite
     */
    public Set<Sprite> getSpritesCollidedWith(Sprite sprite) {
        return null;
    }


    /**
     * Clear the world. This method will remove all the sprites and the states within the world.
     */
    public void clear() {
        //TODO
    }
}
