package com.pokewords.framework.engine.gameworlds;

import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.gameworlds.CollisionHandler.TargetPair;
import com.pokewords.framework.engine.listeners.AppStateLifeCycleListener;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.*;
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
public class AppStateWorld implements AppStateLifeCycleListener, PropertiesComponent.SpritePositionChangedListener {
    protected AppState appState;
    private List<Sprite> sprites;
    private RenderedLayers renderedLayers;
    private Map<TargetPair, List<CollisionHandler>> collisionHandlerMap;

    private Collection<Sprite> readyToBeSpawnedSprites = new LinkedList<>();
    private Collection<Sprite> readyToBeRemovedSprites = new LinkedList<>();

    public AppStateWorld(AppState appState) {
        this.appState = appState;
        sprites = new ArrayList<>(30);
        renderedLayers = new RenderedLayers();
        collisionHandlerMap = new HashMap<>();
    }

    public AppState getAppState() {
        return appState;
    }

    public GameEngineFacade getGameEngineFacade() {
        return getAppState().getGameEngineFacade();
    }

    /**
     * @param sprite the spawned sprite to be added into the world
     * @return the Sprite's unique event
     */
    public void spawn(Sprite sprite) {
        readyToBeSpawnedSprites.add(sprite);
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
        TargetPair collisionHandlerType = collisionHandler.getTargetPair();
        if (!collisionHandlerMap.containsKey(collisionHandlerType))
            collisionHandlerMap.put(collisionHandlerType, new ArrayList<>());

        collisionHandlerMap.get(collisionHandlerType).add(collisionHandler);
    }

    public void removeCollisionHandler(CollisionHandler collisionHandler) {
        List<CollisionHandler> collisionHandlers = collisionHandlerMap.get(collisionHandler.getTargetPair());
        collisionHandlers.remove(collisionHandler);
    }

    /**
     * @return if the sprite is in the world
     */
    public boolean containsSprite(Sprite sprite) {
        return getSprites().contains(sprite);
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
        detectAndHandleCollisions();
    }

    private void handleReadyToBeSpawnedOrRemovedSprites() {
        sprites.addAll(readyToBeSpawnedSprites);
        readyToBeSpawnedSprites.forEach(s -> s.addPositionChangedListener(this));
        readyToBeSpawnedSprites.forEach(s-> s.attachToWorld(this) );
        readyToBeSpawnedSprites.clear();

        sprites.removeAll(readyToBeRemovedSprites);
        readyToBeRemovedSprites.forEach(s -> s.removePositionChangedListener(this));
        readyToBeRemovedSprites.forEach(s-> s.detachFromWorld(this) );
        readyToBeRemovedSprites.clear();
    }

    private void reproduceRenderedLayers() {
        renderedLayers.clearEachLayer();
        sprites.forEach( sprite -> renderedLayers.addFrames(sprite.getRenderedFrames()));
    }

    private void detectAndHandleCollisions() {
        for (int i = 0; i < sprites.size(); i++) {
            for (int j = i + 1; j < sprites.size(); j++) {
                Sprite sprite1 = sprites.get(i);
                Sprite sprite2 = sprites.get(j);

                assert sprite1 != sprite2;

                if (isCollided(sprite1, sprite2)) {
                    notifyCollisionListeners(sprite1, sprite2);
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
     * To trigger a Collision event to interested listeners
     */
    private void notifyCollisionListeners(Sprite sprite1, Sprite sprite2) {
        notifyGlobalCollisionHandlers(sprite1, sprite2);
        notifySpriteCollisionListenerComponents(sprite1, sprite2);
    }

    private void notifyGlobalCollisionHandlers(Sprite sprite1, Sprite sprite2) {
        List<CollisionHandler> collisionHandlers = collisionHandlerMap.getOrDefault(
                        new TargetPair(sprite1.getType(), sprite2.getType()), Collections.emptyList());
        for (CollisionHandler collisionHandler: collisionHandlers) {
            collisionHandler.onCollision(sprite1, sprite2);
        }
    }

    private void notifySpriteCollisionListenerComponents(Sprite sprite1, Sprite sprite2) {
        sprite1.getComponentOptional(CollisionListenerComponent.class)
                .ifPresent(c -> c.getListener().onCollision(sprite1, sprite2, getGameEngineFacade()));
        sprite2.getComponentOptional(CollisionListenerComponent.class)
                .ifPresent(c -> c.getListener().onCollision(sprite2, sprite1, getGameEngineFacade()));
    }

    @Override
    public void onSpritePositionChanged(Sprite sprite) {
        blockTheSpriteIfRigidCollisionOccurs(sprite);
    }

    private void blockTheSpriteIfRigidCollisionOccurs(Sprite sprite) {
        if (sprite.hasComponent(RigidBodyComponent.class))
        {
            Collection<Sprite> collidedRigidBodySprites = getSpritesRigidlyCollidedWith(sprite);

            if (!collidedRigidBodySprites.isEmpty())
            {
                notifySpriteRigidCollisionListener(sprite, collidedRigidBodySprites);
                sprite.resumeToLatestPosition();
            }
        }
    }

    private void notifySpriteRigidCollisionListener(Sprite ownerSprite, Collection<Sprite> collidedRigidBodySprites) {
        if (ownerSprite.hasComponent(CollisionListenerComponent.class))
        {
            ownerSprite.getComponent(CollisionListenerComponent.class)
                        .getListener().onRigidCollisionEvent(ownerSprite, getGameEngineFacade());

            for (Sprite collidedRigidBodySprite : collidedRigidBodySprites) {
                collidedRigidBodySprite.getComponent(CollisionListenerComponent.class)
                        .getListener().onRigidCollisionEvent(collidedRigidBodySprite, getGameEngineFacade());

                ownerSprite.getComponent(CollisionListenerComponent.class)
                        .getListener().onRigidCollisionToSprite(ownerSprite, collidedRigidBodySprite, getGameEngineFacade());

                collidedRigidBodySprite.getComponent(CollisionListenerComponent.class)
                        .getListener().onRigidCollisionToSprite(collidedRigidBodySprite, ownerSprite, getGameEngineFacade());
            }
        }
    }


    /**
     * @return a set of rigid-body sprites collided with the given sprite
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
     * @return a set of sprites within the area (w, h) from the center point of the given sprite
     */
    public Collection<Sprite> getSpritesCollidedWithinArea(Sprite sprite, Dimension dimension) {
        return getSpritesCollidedWithinArea(sprite, dimension.width, dimension.height);
    }

    /**
     * @return a set of sprites within the area (w, h) from the center point of the given sprite
     */
    public Collection<Sprite> getSpritesCollidedWithinArea(Sprite sprite, int w, int h) {
        Point2D center = sprite.getCenter();
        int x = (int) center.getX() - w / 2;
        int y = (int) center.getY() - h / 2;
        Collection<Sprite> collidedSprites = getSpritesCollidedWithinArea(new Rectangle(x, y, w, h));
        collidedSprites.remove(sprite);
        return collidedSprites;
    }

    /**
     * @return a set of sprites within the area (x, y, w, h)
     */
    public Collection<Sprite> getSpritesCollidedWithinArea(int x, int y, int w, int h) {
        return getSpritesCollidedWithinArea(new Rectangle(x, y, w, h));
    }

    /**
     * @return a set of sprites within the area (x, y, w, h)
     */
    public Collection<Sprite> getSpritesCollidedWithinArea(Rectangle area) {
        return sprites.stream()
                .filter(sprite -> area.intersects(sprite.getBody()))
                .collect(Collectors.toList());
    }

    /**
     * @return a set of sprites collided with the given sprite
     */
    public Collection<Sprite> getSpritesCollidedWith(Sprite sprite) {
        Collection<Sprite> collidedSprites = getSpritesCollidedWithinArea(sprite.getBody());
        collidedSprites.remove(sprite);
        return collidedSprites;
    }


    /**
     * Remove all the sprites.
     */
    public void clearSprites() {
        sprites.forEach(this::removeSprite);
        renderedLayers.clear();
        System.gc();
    }

    /**
     * Detach the sprite from the world instantly and remove the given sprite in the next game-loop.
     */
    public void removeSprite(Sprite sprite) {
        readyToBeRemovedSprites.add(sprite);
    }

    /**
     * Clear the global collision handlers.
     */
    public void clearCollisionHandlers() {
        collisionHandlerMap.clear();
    }

    /**
     * @return a set of MouseListenerComponents each owned by a certain Sprite in the world.
     */
    public Collection<MouseListenerComponent> getMouseListenerComponents() {
        return sprites.stream().filter(s -> s.hasComponent(MouseListenerComponent.class))
                        .map(s -> s.getComponent(MouseListenerComponent.class))
                        .collect(Collectors.toList());
    }

    /**
     * @return a set of KeyListenerComponent each owned by a certain Sprite in the world.
     */
    public Collection<KeyListenerComponent> getKeyListenerComponents() {
        return sprites.stream().filter(s -> s.hasComponent(KeyListenerComponent.class))
                .map(s -> s.getComponent(KeyListenerComponent.class))
                .collect(Collectors.toList());
    }

}
