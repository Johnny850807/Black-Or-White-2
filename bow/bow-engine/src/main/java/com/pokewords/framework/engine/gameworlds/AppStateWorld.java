package com.pokewords.framework.engine.gameworlds;

import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.listeners.AppStateLifeCycleListener;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.KeyListenerComponent;
import com.pokewords.framework.sprites.components.MouseListenerComponent;
import com.pokewords.framework.sprites.components.RigidBodyComponent;
import com.pokewords.framework.views.RenderedLayers;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Joanna, johnny850807 (waterball)
 */
public class AppStateWorld implements AppStateLifeCycleListener {
    protected AppState appState;
    protected GameEngineFacade gameEngineFacade;
    private List<Sprite> sprites;
    private RenderedLayers renderedLayers;
    private Map<CollisionHandler.TargetPair, List<CollisionHandler>> collisionHandlerMap;

    private Collection<Sprite> readyToBeSpawnedSprites = new LinkedList<>();
    private Collection<Sprite> readyToBeRemovedSprites = new LinkedList<>();

    public AppStateWorld(AppState appState, GameEngineFacade gameEngineFacade) {
        this.appState = appState;
        this.gameEngineFacade = gameEngineFacade;
        sprites = new ArrayList<>(30);
        renderedLayers = new RenderedLayers();
        collisionHandlerMap = new HashMap<>();
    }

    /**
     * @param sprite the spawned sprite to be added into the world
     * @return the Sprite's unique event
     */
    public void spawn(Sprite sprite) {
        readyToBeSpawnedSprites.add(sprite);
        sprite.setWorld(this);
    }

    /**
     * TODO Executors.schedule....
     * @param sprite the spawned sprite to be added into the world
     * @param time the time-delay to spawn
     * @param timeUnit the time unit of
     * @param callback the callback receives the spawned sprite's event when the sprite is actually spawned
     */
    public void spawnDelay(Sprite sprite, int time, TimeUnit timeUnit, @Nullable Runnable callback) {
        new Thread(() -> {
            try {
                timeUnit.sleep(time);
                spawn(sprite);
                if (callback != null) {
                    callback.run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public RenderedLayers getRenderedLayers() {
        return renderedLayers;
    }

    public void addCollisionHandler(CollisionHandler collisionHandler) {
        CollisionHandler.TargetPair collisionHandlerType = new CollisionHandler.TargetPair(collisionHandler.getFirstType(), collisionHandler.getSecondType());
        if (!collisionHandlerMap.containsKey(collisionHandlerType))
            collisionHandlerMap.put(collisionHandlerType, new ArrayList<>());

        collisionHandlerMap.get(collisionHandlerType).add(collisionHandler);
    }

    public void removeCollisionHandler(CollisionHandler collisionHandler) {
        List<CollisionHandler> collisionHandlers = collisionHandlerMap.get(collisionHandler.getTargetPair());
        collisionHandlers.remove(collisionHandler);
    }

    public Collection<Sprite> getSprites() {
        return sprites;
    }

    public Collection<CollisionHandler> getCollisionHandlers() {
        List<CollisionHandler> collisionHandlers = new ArrayList<>();
        for (List<CollisionHandler> collisionHandlerList: collisionHandlerMap.values()) {
            collisionHandlers.addAll(collisionHandlerList);
        }
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
    public void onUpdate(double timePerFrame) {
        handleReadyToBeSpawnedOrRemovedSprites();

        for (Sprite sprite: sprites) {
            sprite.onUpdate(timePerFrame);
        }

        reproduceRenderedLayers();
        detectCollidedSpritesAndNotifyCollisionHandlers();
    }

    private void handleReadyToBeSpawnedOrRemovedSprites() {
        sprites.removeAll(readyToBeRemovedSprites);
        readyToBeRemovedSprites.clear();
        sprites.addAll(readyToBeSpawnedSprites);
        readyToBeSpawnedSprites.clear();
    }

    private void reproduceRenderedLayers() {
        renderedLayers.clearEachLayer();
        sprites.forEach( sprite -> renderedLayers.addFrames(sprite.getRenderedFrames()));
    }

    private void detectCollidedSpritesAndNotifyCollisionHandlers() {
        for (int i = 0; i < sprites.size(); i++) {
            for (int j = i + 1; j < sprites.size(); j++) {
                Sprite sprite1 = sprites.get(i);
                Sprite sprite2 = sprites.get(j);

                assert sprite1 != sprite2;

                if (isCollided(sprite1, sprite2)) {
                    notifyCollisionHandlers(sprite1, sprite2);
                }
            }
        }
    }


    /**
     * To notify sprites if they have collided
     */
    private void notifyCollisionHandlers(Sprite sprite1, Sprite sprite2) {
        List<CollisionHandler> collisionHandlers = collisionHandlerMap.get(new CollisionHandler.TargetPair(sprite1.getType(), sprite2.getType()));
        for (CollisionHandler collisionHandler: collisionHandlers) {
            collisionHandler.onCollision(sprite1, sprite2);
        }
    }

    public void handleSpriteRigidCollisionDetection(Sprite sprite, Runnable taskIfInvalidPosition) {
        if (sprite.hasComponent(RigidBodyComponent.class))
        {
            if (!getSpritesRigidlyCollidedWith(sprite).isEmpty())
                taskIfInvalidPosition.run();
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
        if (sprite1.getCollidableComponent().isIgnored(sprite2.getType()) &&
                sprite2.getCollidableComponent().isIgnored(sprite1.getType()))
            return false;
        return sprite1.getBody().intersects(sprite2.getBody());
    }

    /**
     * @return if the sprite is in the world
     */
    public boolean contains(Sprite sprite) {
        return sprites.contains(sprite);
    }


    /**
     * @return the sprites within the area (w, h) from the center point of the given sprite
     */
    public Collection<Sprite> getSpritesCollidedWithinArea(Sprite sprite, Dimension dimension) {
        return getSpritesCollidedWithinArea(sprite, dimension.width, dimension.height);
    }

    /**
     * @return the sprites within the area (w, h) from the center point of the given sprite
     */
    public Collection<Sprite> getSpritesCollidedWithinArea(Sprite sprite, int w, int h) {
        Point2D center = sprite.getCenter();
        int x = (int) center.getX() - w / 2;
        int y = (int) center.getY() - h / 2;
        Set<Sprite> sprites = Collections.newSetFromMap( new IdentityHashMap<>());
        sprites.addAll(getSpritesCollidedWithinArea(new Rectangle(x, y, w, h)));
        sprites.remove(sprite);
        return sprites;
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
     * @return the sprites collided with the given sprite
     */
    public Collection<Sprite> getSpritesCollidedWith(Sprite sprite) {
        Set<Sprite> sprites = Collections.newSetFromMap(new IdentityHashMap<>());
        sprites.addAll(getSpritesCollidedWithinArea(sprite.getBody()));
        sprites.remove(sprite);
        return sprites;
    }

    /**
     * @return the rigid-body sprites collided with the given sprite
     */
    public Collection<Sprite> getSpritesRigidlyCollidedWith(Sprite sprite) {
        Set<Sprite> sprites = Collections.newSetFromMap(new IdentityHashMap<>());
        Collection<Sprite> rigidCollidedSprites = getSpritesCollidedWithinArea(sprite.getBody())
                                                    .stream().filter(s -> s.hasComponent(RigidBodyComponent.class))
                                                                .collect(Collectors.toList());

        sprites.addAll(rigidCollidedSprites);
        sprites.remove(sprite);
        return sprites;
    }


    /**
     * Clear the world. This method will remove all the sprites and the states within the world.
     */
    public void clearSprites() {
        sprites.forEach(this::removeSprite);
        renderedLayers.clear();
        System.gc();
    }

    public void removeSprite(Sprite sprite) {
        readyToBeRemovedSprites.add(sprite);
        sprite.setWorld(null);
    }

    public void clearCollisionHandlers() {
        collisionHandlerMap.clear();
    }

    public Collection<MouseListenerComponent> getMouseListenerComponents() {
        return sprites.stream().filter(s -> s.hasComponent(MouseListenerComponent.class))
                        .map(s -> s.getComponent(MouseListenerComponent.class))
                        .collect(Collectors.toList());
    }

    public Collection<KeyListenerComponent> getKeyListenerComponents() {
        return sprites.stream().filter(s -> s.hasComponent(KeyListenerComponent.class))
                .map(s -> s.getComponent(KeyListenerComponent.class))
                .collect(Collectors.toList());
    }
}
