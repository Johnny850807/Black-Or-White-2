package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;

/**
 * @author johnny850807 (waterball)
 */
public class CollisionListenerComponent extends CloneableComponent {

    /**
     * This method will be triggered during appStateWorld updating and
     * if the owner Sprite is collided with another non-rigid-body Sprite.
     * @param collidedSprite the collided sprite
     */
    public void onCollisionWithSprite(Sprite collidedSprite) {}


    /**
     * This method will be triggered during the owner Sprite is being moved and
     * if this movement causes a collision event to the owner Sprite.
     *
     * Note that this method will be triggered only once in every getMovement despite the number
     * of rigid sprites it collides with.
     */
    public void onRigidCollisionEvent() {}


    /**
     * This method will be triggered during the owner Sprite is being moved and
     * if the owner Sprite is collided with another rigid-body Sprite.
     *
     * Note that this method will be triggered for each rigid Sprite it collides with.
     *
     * @param collidedSprite the sprite collided with
     */
    public void onRigidCollisionWithSprite(Sprite collidedSprite) {}

}
