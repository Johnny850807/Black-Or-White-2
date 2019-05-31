package com.pokewords.framework.sprites.components;

import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.sprites.Sprite;

/**
 * @author johnny850807 (waterball)
 */
public class CollisionListenerComponent extends CloneableComponent {
    private Listener listener;

    public static CollisionListenerComponent ofListener(Listener listener) {
        return new CollisionListenerComponent(listener);
    }

    private CollisionListenerComponent(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onUpdate(double timePerFrame) {
        listener.onUpdate(timePerFrame, getOwnerSprite());
    }

    public static abstract class Listener implements Cloneable {


        public void onUpdate(double timePerFrame, Sprite ownerSprite) {
            //hook
        }

        /**
         * This method will be triggered during appStateWorld updating and
         * if the owner Sprite is collided with another non-rigid-body Sprite.
         * @param ownerSprite the owner sprite of the listener
         * @param thatSprite the sprite collided with
         */
        public abstract void onCollisionWithSprite(Sprite ownerSprite, Sprite thatSprite, GameEngineFacade gameEngineFacade);


        /**
         * This method will be triggered during the owner Sprite is being moved and
         * if this movement causes a collision event to the owner Sprite.
         *
         * Note that this method will be triggered only once in every getMovement despite the number
         * of rigid sprites it collides with.
         * @param ownerSprite the owner sprite of the listener
         */
        public abstract void onRigidCollisionEvent(Sprite ownerSprite, GameEngineFacade gameEngineFacade);


        /**
         * This method will be triggered during the owner Sprite is being moved and
         * if the owner Sprite is collided with another rigid-body Sprite.
         *
         * Note that this method will be triggered for each rigid Sprite it collides with.
         *
         * @param ownerSprite the owner sprite of the listener
         * @param thatSprite the sprite collided with
         */
        public abstract void onRigidCollisionWithSprite(Sprite ownerSprite, Sprite thatSprite, GameEngineFacade gameEngineFacade);

        public Listener clone() {
            try {
                return (Listener) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new Error(e);
            }
        }
    }

    public Listener getListener() {
        return listener;
    }

    public void notifyCollisionWithSprite(Sprite collidedSprite) {
        getListener().onCollisionWithSprite(getOwnerSprite(), collidedSprite, getGameEngineFacade());
    }

    public void notifyRigidCollisionEvent() {
        getListener().onRigidCollisionEvent(getOwnerSprite(), getGameEngineFacade());
    }

    public void notifyRigidCollisionWithSprite(Sprite collidedSprite) {
        getListener().onRigidCollisionWithSprite(getOwnerSprite(), collidedSprite, getGameEngineFacade());
    }

    @Override
    public CollisionListenerComponent clone() {
        CollisionListenerComponent clone = (CollisionListenerComponent) super.clone();
        clone.listener = listener.clone();
        return clone;
    }
}
