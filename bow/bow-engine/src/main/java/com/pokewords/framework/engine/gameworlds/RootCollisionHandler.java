package com.pokewords.framework.engine.gameworlds;

import com.pokewords.framework.sprites.Sprite;

/**
 * @author johnny850807 (waterball)
 */
public interface RootCollisionHandler {
    void onCollision(Sprite s1, Sprite s2);
    void onRigidCollisionAtOneMovement(Sprite collidedSprite);
    void onRigidCollision(Sprite s1, Sprite s2);
}
