package com.pokewords.framework.engine.gameworlds;

import com.pokewords.framework.commons.Direction;
import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.gameworlds.CollisionHandler.TargetPair;
import com.pokewords.framework.engine.listeners.AppStateLifeCycleListener;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.*;
import com.pokewords.framework.views.RenderedLayers;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Joanna, johnny850807 (waterball)
 */
public class AppStateWorld implements AppStateLifeCycleListener, PropertiesComponent.SpritePositionChangedListener {
    private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
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
     * Add a Sprite into the spawn-list, the sprite will be added in the next game loop.
     * @param sprite the spawned sprite to be added into the world
     */
    public void spawn(Sprite sprite) {
        readyToBeSpawnedSprites.add(sprite);
    }

    /**
     * Spawn a sprite after certain time.
     * @param sprite the spawned sprite to be added into the world
     * @param time the time-delay to spawn
     * @param timeUnit the time unit
     */
    public void spawnDelay(Sprite sprite, int time, TimeUnit timeUnit) {
        spawnDelay(sprite, time, timeUnit, ()->{});
    }

    /**
     * Spawn a sprite after certain time.
     * @param sprite the spawned sprite to be added into the world
     * @param time the time-delay to spawn
     * @param timeUnit the time unit
     * @param spawnCallback the callback when the sprite is actually spawned
     */
    public void spawnDelay(Sprite sprite, int time, TimeUnit timeUnit, Runnable spawnCallback) {
        scheduler.schedule(() -> {
            spawn(sprite);
            spawnCallback.run();
        }, time, timeUnit);
    }

    /**
     * @return the pre-produced renderedLayers
     */
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
     * @return whether the sprite is in the world
     */
    public boolean containsSprite(Sprite sprite) {
        return getSprites().contains(sprite) || readyToBeSpawnedSprites.contains(sprite);
    }

    /**
     * @return all the sprites in the world
     */
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
            sprite.attachToWorld(this);
        }
    }

    @Override
    public void onAppStateExit() {
        for (Sprite sprite: sprites) {
            sprite.onAppStateExit();
            sprite.detachFromWorld(this);
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
        readyToBeSpawnedSprites.stream()
                .flatMap(s -> s.getComponents().stream())
                .forEach(c -> c.setGameEngineFacade(getGameEngineFacade()));
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
        sprite1.locateComponents(CollisionListenerComponent.class)
                .forEach(c -> c.onCollisionWithSprite(sprite2));
        sprite2.locateComponents(CollisionListenerComponent.class)
                .forEach(c -> c.onCollisionWithSprite(sprite1));
    }

    @Override
    public void onSpritePositionChanged(Sprite sprite) {
        blockTheSpriteIfRigidCollisionOccurs(sprite);
    }

    private void blockTheSpriteIfRigidCollisionOccurs(Sprite sprite) {
        Collection<Sprite> rigidlyCollidedSprites = getSpritesRigidlyCollidedWith(sprite);

        if (!rigidlyCollidedSprites.isEmpty()) {
            notifySpriteRigidCollisionListener(sprite, rigidlyCollidedSprites);
        }
    }

    /**
     * @return a set of sprites that rigidly collided with the given sprite
     */
    public Collection<Sprite> getSpritesRigidlyCollidedWith(Sprite sprite) {
        if (!sprite.isRigidBody())  //the sprite is not rigid-body then it won't have any rigid collisions with any sprites
            return Collections.emptySet();

        Collection<Sprite> rigidCollidedSprites = getSpritesIntersectWithArea(sprite.getBody())
                .stream().filter(Sprite::isRigidBody)
                .collect(Collectors.toList());

        rigidCollidedSprites.remove(sprite);
        return rigidCollidedSprites;
    }

    private void notifySpriteRigidCollisionListener(Sprite collidingSprite, Collection<Sprite> rigidlyCollidedSprites) {
        collidingSprite.locateComponents(CollisionListenerComponent.class)
                .forEach(CollisionListenerComponent::onRigidCollisionEvent);

        for (Sprite rigidCollidedSprite : rigidlyCollidedSprites) {
            rigidCollidedSprite.locateComponents(CollisionListenerComponent.class)
                    .forEach(CollisionListenerComponent::onRigidCollisionEvent);

            collidingSprite.locateComponents(CollisionListenerComponent.class)
                    .forEach(c -> c.onRigidCollisionWithSprite(rigidCollidedSprite));

            rigidCollidedSprite.locateComponents(CollisionListenerComponent.class)
                    .forEach(c -> c.onRigidCollisionWithSprite(collidingSprite));

            resolveRigidCollisionPositions(collidingSprite, rigidCollidedSprite);
        }
    }


    private void resolveRigidCollisionPositions(Sprite collidingSprite, Sprite collidedSprite) {
        Direction bodyMovingDirection = Direction.getAtomicDirectionFromOnePointToAnother(
                collidingSprite.getLatestProperties().body.getLocation(), collidingSprite.getBody().getLocation());
        Rectangle intersection = collidingSprite.getBody().intersection(collidedSprite.getBody());
        switch (bodyMovingDirection) {
            case NO_DIRECTION:
                break;
            case RIGHT:
                collidingSprite.moveX((-1) * intersection.width);
                break;
            case LEFT:
                collidingSprite.moveX(intersection.width);
                break;
            case UP:
                collidingSprite.moveY(intersection.height);
                break;
            case DOWN:
                collidingSprite.moveY((-1) * intersection.height);
                break;
            default:
                throw new InternalError("The bodyMovingDirection is atomic.");
        }
    }


    /**
     * @return a set of sprites with the area (w, h) from the center point of the given sprite
     */
    public Collection<Sprite> getSpritesIntersectWithArea(Sprite sprite, Dimension dimension) {
        return getSpritesIntersectWithArea(sprite, dimension.width, dimension.height);
    }

    /**
     * @return a set of sprites within the area (w, h) from the center point of the given sprite
     */
    public Collection<Sprite> getSpritesIntersectWithArea(Sprite sprite, int w, int h) {
        Point2D center = sprite.getCenter();
        int x = (int) center.getX() - w / 2;
        int y = (int) center.getY() - h / 2;
        Collection<Sprite> collidedSprites = getSpritesIntersectWithArea(new Rectangle(x, y, w, h));
        collidedSprites.remove(sprite);
        return collidedSprites;
    }

    /**
     * @return a set of sprites intersect with the area (x, y, w, h)
     */
    public Collection<Sprite> getSpritesIntersectWithArea(int x, int y, int w, int h) {
        return getSpritesIntersectWithArea(new Rectangle(x, y, w, h));
    }

    /**
     * @return a set of sprites intersect with the area (x, y, w, h)
     */
    public Collection<Sprite> getSpritesIntersectWithArea(Rectangle area) {
        return sprites.stream()
                .filter(sprite -> area.intersects(sprite.getBody()))
                .collect(Collectors.toList());
    }

    /**
     * @return a set of sprites collided with the given sprite
     */
    public Collection<Sprite> getSpritesCollidedWith(Sprite sprite) {
        Collection<Sprite> collidedSprites = getSpritesIntersectWithArea(sprite.getBody());
        collidedSprites.remove(sprite);
        return collidedSprites;
    }


    /**
     * Remove all the sprites.
     */
    public void clearSprites() {
        sprites.forEach(this::removeSprite);
        readyToBeSpawnedSprites.clear();
        readyToBeRemovedSprites.clear();
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
        return sprites.stream()
                        .flatMap(s -> s.locateComponents(MouseListenerComponent.class).stream())
                        .collect(Collectors.toList());
    }

    /**
     * @return a set of KeyListenerComponent each owned by a certain Sprite in the world.
     */
    public Collection<KeyListenerComponent> getKeyListenerComponents() {
        return sprites.stream()
                .flatMap(s -> s.locateComponents(KeyListenerComponent.class).stream())
                .collect(Collectors.toList());
    }

}
