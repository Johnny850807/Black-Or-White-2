package com.pokewords.framework.engine.gameworlds;

import com.pokewords.framework.engine.exceptions.GameEngineException;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.engine.listeners.AppStateLifeCycleListener;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.views.RenderedLayers;
import org.jetbrains.annotations.Nullable;


import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Joanna
 */
public class AppStateWorld implements AppStateLifeCycleListener {
    private List<Sprite> sprites;
    private AtomicInteger spriteCount;
    private Map<Integer, Sprite> idSpriteMap;
    private Map<Sprite, Integer> spriteIdMap;
    private RenderedLayers renderedLayers;
    private List<CollisionHandler> collisionHandlers;

    public AppStateWorld() {
        sprites = new ArrayList<>();
        spriteCount = new AtomicInteger(0);
        renderedLayers = new RenderedLayers(new ArrayList<>());
        collisionHandlers = new ArrayList<>();
        idSpriteMap = new HashMap<>();
        spriteIdMap = new IdentityHashMap<>();
    }

    /**
     * @param sprite the spawned sprite to be added into the world
     * @return the Sprite's unique id
     */
    public int spawn(Sprite sprite) {
        int id = spriteCount.incrementAndGet();
        addSpriteIntoWorld(id, sprite);
        addFramesToRenderedLayer(sprite.getRenderedFrames());
        return id;
    }

    private void addSpriteIntoWorld(int id, Sprite sprite) {
        sprites.add(sprite);
        idSpriteMap.put(id, sprite);
        spriteIdMap.put(sprite, id);
    }

    /**
     * TODO Executors.schedule....
     * @param sprite the spawned sprite to be added into the world
     * @param time the time-delay to spawn
     * @param timeUnit the time unit of
     * @param callback the callback receives the spawned sprite's id when the sprite is actually spawned
     */
    public void spawnDelay(Sprite sprite, int time, TimeUnit timeUnit, @Nullable Consumer<Integer> callback) {
        new Thread(() -> {
            try {
                timeUnit.sleep(time);
                int id = spawn(sprite);
                if (callback != null) {
                    callback.accept(id);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Add the frames to the rendered layer.
     * @param renderedFrames The Frame will be add in the rendered layer.
     */
    private void addFramesToRenderedLayer(Collection<Frame> renderedFrames) {
        for (Frame frame: renderedFrames) {
            addFrameToRenderedLayer(frame, frame.getLayerIndex());
        }
    }

    /**
     * Add the frame to the rendered layer.
     * @param frame The Frame.
     * @param layerIndex The index of the frame in rendered layer.
     */
    private void addFrameToRenderedLayer(Frame frame, int layerIndex) {
        List<List<Frame>> layers = renderedLayers.getLayers();
        expandLayersToFitTheIndex(layers, layerIndex);
        layers.get(layerIndex).add(frame);
    }

    private void expandLayersToFitTheIndex(List<List<Frame>> layers, int layerIndex) {
        IntStream.range(layers.size(), layerIndex)
                .forEach(i -> layers.add(new ArrayList<>()));
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
    public void onAppStateCreate(AppStateWorld world) {
        if (world != this) {
            throw new GameEngineException("The world is not consistent from triggering the onAppStateCreate() method from the AppState");
        }

        for (Sprite sprite: sprites) {
            sprite.onAppStateCreate(this);
        }
    }

    @Override
    public void onAppStateEnter() {
        for (Sprite sprite: sprites) {
            sprite.onAppStateEnter();
        }
    }

    @Override
    public void onAppStateExit() {
        for (Sprite sprite: sprites) {
            sprite.onAppStateExit();
        }
    }

    @Override
    public void onAppStateDestroy() {
        for (Sprite sprite: sprites) {
            sprite.onAppStateDestroy();
        }
    }


    @Override
    public void onUpdate(int timePerFrame) {
        for (Sprite sprite: sprites) {
            sprite.onUpdate(timePerFrame);
        }
        notifyCollisionHandler();
    }

    /**
     * To notify sprites if they have collided
     * //TODO 我會被扣兩次血
     */
    private void notifyCollisionHandler() {
        for (Sprite sprite1: sprites) {
            for (Sprite sprite2: sprites) {
                if (sprite1 != sprite2 && isCollided(sprite1, sprite2)) {
                    for (CollisionHandler collisionHandler: collisionHandlers) {
                        collisionHandler.onCollision(sprite1, sprite2);
                    }
                }
            }
        }
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

    /**
     * @return if the sprite is in the world
     */
    public boolean contains(Sprite sprite) {
        return spriteIdMap.containsKey(sprite);
    }

    /**
     * @return if the sprite who owns the id is in the world
     */
    public boolean contains(int spriteId) {
        return idSpriteMap.containsKey(spriteId);
    }

    /**
     * @return the sprite's id
     */
    public int getId(Sprite sprite) {
        return spriteIdMap.get(sprite);
    }

    /**
     * @return the sprite who owns the id
     */
    public Sprite getSprite(int spriteId) {
        return idSpriteMap.get(spriteId);
    }

    /**
     * @return the sprites within the area (x, y, w, h)
     */
    public Collection<Sprite> getSpritesWithinArea(int x, int y, int w, int h) {
        return getSpritesWithinArea(new Rectangle(x, y, w, h));
    }

    /**
     * @return the sprites within the area (x, y, w, h)
     */
    public Collection<Sprite> getSpritesWithinArea(Rectangle area) {
        return sprites.stream()
                    .filter(sprite -> area.contains(sprite.getBody()))
                    .collect(Collectors.toList());
    }


    /**
     * @return the sprites within the area (w, h) from the center point of the given sprite
     */
    public Collection<Sprite> getSpritesWithinArea(Sprite sprite, int w, int h) {
        Point2D center = sprite.getCenter();
        int x = (int) center.getX() - w / 2;
        int y = (int) center.getY() - h / 2;
        return getSpritesWithinArea(new Rectangle(x, y, w, h));
    }

    /**
     * @return the sprites within the area (x, y, w, h) from the center point of the given sprite
     */
    public Collection<Sprite> getSpritesWithinArea(Sprite sprite, Dimension dimension) {
        return getSpritesWithinArea(sprite, dimension.width, dimension.height);
    }

    /**
     * @return the sprites collided with the given sprite
     */
    public Collection<Sprite> getSpritesCollidedWith(Sprite sprite) {
        return getSpritesWithinArea(sprite.getBody());
    }


    /**
     * Clear the world. This method will remove all the sprites and the states within the world.
     */
    public void clearSprites() {
        sprites.clear();
        spriteCount = new AtomicInteger(0);
        renderedLayers.setLayers(new ArrayList<>());
        idSpriteMap.clear();
        spriteIdMap.clear();
    }

    public void clearCollisionHandlers() {
        collisionHandlers.clear();
    }
}