package com.pokewords.framework.engine.gameworlds;

import com.pokewords.framework.engine.exceptions.GameEngineException;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.engine.listeners.AppStateLifeCycleListener;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.views.RenderedLayers;
import org.jetbrains.annotations.Nullable;
import sun.awt.util.IdentityArrayList;


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
        sprite.setWorld(this);
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
    private void addFramesToRenderedLayer(Collection<? extends Frame> renderedFrames) {
        for (Frame frame: renderedFrames) {
            renderedLayers.addFrame(frame, frame.getLayerIndex());
        }
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
    public void onAppStateCreate() {
        for (Sprite sprite: sprites) {
            sprite.onAppStateCreate();
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
        rejoinRenderLayers();
        findCollidedSpritesAndNotifyCollisionHandlers();
    }

    private void rejoinRenderLayers() {
        for (Sprite sprite: sprites) {
            addFramesToRenderedLayer(sprite.getRenderedFrames());
        }
    }

    private void findCollidedSpritesAndNotifyCollisionHandlers() {
        for (int i = 0; i < sprites.size(); i++) {
            for (int j = i + 1; j < sprites.size(); j++) {
                Sprite sprite1 = sprites.get(i);
                Sprite sprite2 = sprites.get(j);
                if (sprite1 != sprite2 && isCollided(sprite1, sprite2)) {
                    notifyCollisionHandlers(sprite1, sprite2);
                }
            }
        }
    }

    /**
     * To notify sprites if they have collided
     * //TODO 我會被扣兩次血
     */
    private void notifyCollisionHandlers(Sprite sprite1, Sprite sprite2) {
        for (CollisionHandler collisionHandler: collisionHandlers) {
            if ((collisionHandler.s1Type.equals(sprite1.getType()) && collisionHandler.s2Type.equals(sprite2.getType())) ||
                    (collisionHandler.s1Type.equals(sprite2.getType()) && collisionHandler.s2Type.equals(sprite1.getType())))
                collisionHandler.onCollision(sprite1, sprite2);
        }
    }

    /**
     * To know if two sprites have a collision.
     * @param sprite1 One of two sprites.
     * @param sprite2 Another one of two sprites.
     * @return true if two sprites have a collision.
     */
    private boolean isCollided(Sprite sprite1, Sprite sprite2) {
        if (!sprite1.isCollidable() || !sprite2.isCollidable())
            return false;
        if (sprite1.getCollidableComponent().isIgnored(sprite2) &&
                sprite2.getCollidableComponent().isIgnored(sprite1))
            return false;
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
    public Collection<Sprite> getSpritesCollidedWithinArea(int x, int y, int w, int h) {
        return getSpritesCollidedWithinArea(new Rectangle(x, y, w, h));
    }

    /**
     * @return the sprites within the area (x, y, w, h)
     */
    public Collection<Sprite> getSpritesCollidedWithinArea(Rectangle area) {
        return sprites.stream()
                .filter(sprite -> area.intersects(sprite.getBody()))
                .collect(Collectors.toList());
    }


    /**
     * @return the sprites within the area (w, h) from the center point of the given sprite
     */
    public Collection<Sprite> getSpritesCollidedWithinArea(Sprite sprite, int w, int h) {
        Point2D center = sprite.getCenter();
        int x = (int) center.getX() - w / 2;
        int y = (int) center.getY() - h / 2;
        IdentityArrayList<Sprite> sprites = (IdentityArrayList<Sprite>) getSpritesCollidedWithinArea(new Rectangle(x, y, w, h));
        sprites.remove(sprite);
        return sprites;
    }

    /**
     * @return the sprites within the area (w, h) from the center point of the given sprite
     */
    public Collection<Sprite> getSpritesCollidedWithinArea(Sprite sprite, Dimension dimension) {
        return getSpritesCollidedWithinArea(sprite, dimension.width, dimension.height);
    }

    /**
     * @return the sprites collided with the given sprite
     */
    public Collection<Sprite> getSpritesCollidedWith(Sprite sprite) {
        IdentityArrayList<Sprite> sprites = (IdentityArrayList<Sprite>) getSpritesCollidedWithinArea(sprite.getBody());
        sprites.remove(sprite);
        return sprites;
    }


    /**
     * Clear the world. This method will remove all the sprites and the states within the world.
     */
    public void clearSprites() {
        sprites.forEach(this::removeSprite);
        spriteCount = new AtomicInteger(0);
        renderedLayers.setLayers(new ArrayList<>());
        idSpriteMap.clear();
        spriteIdMap.clear();
        System.gc();
    }

    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
        sprite.setWorld(null);
    }

    public void clearCollisionHandlers() {
        collisionHandlers.clear();
    }
}
