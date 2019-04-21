package com.pokewords.framework.sprites.components.gameworlds;

import com.pokewords.framework.engine.exceptions.GameEngineException;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.AppStateLifeCycleListener;
import com.pokewords.framework.sprites.components.Frame;
import com.pokewords.framework.views.RenderedLayers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joanna
 */

public class AppStateWorld implements AppStateLifeCycleListener {
    private List<Sprite> sprites;
    private RenderedLayers renderedLayers;
    private List<CollisionHandler> collisionHandlers;

    public AppStateWorld() {
        sprites = new ArrayList<>();
        List<List<Frame>> layers = new ArrayList<>();
        renderedLayers = new RenderedLayers(layers);
        collisionHandlers = new ArrayList<>();
    }

    /**
     * To add sprite in the world.
     * @param sprite The Sprite.
     */
    public void spawn(Sprite sprite) {
        sprites.add(sprite);
        addFrameToRenderedLayer(sprite.getCurrentFrame().getLayerIndex(), sprite.getCurrentFrame());
    }

    /**
     * Add the frame to the rendered layer.
     * @param index The index of the frame in rendered layer.
     * @param frame The Frame.
     */
    private void addFrameToRenderedLayer(int index, Frame frame) {
        List<List<Frame>> layers = renderedLayers.getLayers();
        if (index >= layers.size() - 1)
            for (int i = 0; i < layers.size() - index + 1; i++)
                layers.add(new ArrayList<>());
        layers.get(index).add(frame);
    }

    public RenderedLayers getRenderedLayers() {
        return renderedLayers;
    }

    public void addCollisionHandler(CollisionHandler collisionHandler) {
        collisionHandlers.add(collisionHandler);
    }

    public void removeCollisionHandler(CollisionHandler collisionHandler) {
        collisionHandlers.remove(collisionHandler);
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    public List<CollisionHandler> getCollisionHandlers() {
        return collisionHandlers;
    }

    @Override
    public void onAppStateStart(AppStateWorld world) {
        if (world != this)
            throw new GameEngineException("The world is not consistent from triggering the onAppStateStart() method from the AppState");

        for (Sprite sprite: sprites)
            sprite.onAppStateStart(this);
    }

    @Override
    public void onAppStateEnter() {
        for (Sprite sprite: sprites)
            sprite.onAppStateEnter();
    }

    @Override
    public void onAppStateExit() {
        for (Sprite sprite: sprites)
            sprite.onAppStateExit();
    }

    @Override
    public void onAppStateDestroy() {
        for (Sprite sprite: sprites)
            sprite.onAppStateDestroy();
    }


    @Override
    public void onUpdate(double tpf) {
        for (Sprite sprite: sprites)
            sprite.onUpdate(tpf);

        // to notify sprites if they have collided
        for (Sprite sprite1: sprites)
            for (Sprite sprite2: sprites)
                if (sprite1 != sprite2 && isCollided(sprite1, sprite2))
                    for (CollisionHandler collisionHandler: collisionHandlers)
                        collisionHandler.onCollision(sprite1, sprite2);
    }

    /**
     * To know if two sprites have a collision.
     * @param sprite1 One of two sprites.
     * @param sprite2 Another one of two sprites.
     * @return true if two sprites have a collision.
     */
    private boolean isCollided(Sprite sprite1, Sprite sprite2) {
        return sprite1.getBody().intersects(sprite2.getBody());
    }
}
