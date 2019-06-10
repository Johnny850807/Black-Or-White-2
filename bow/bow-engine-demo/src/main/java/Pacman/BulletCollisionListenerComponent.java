package Pacman;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.CollisionListenerComponent;
import com.pokewords.framework.sprites.components.HpComponent;

public class BulletCollisionListenerComponent extends CollisionListenerComponent {
    @Override
    public void onCollisionWithSprite(Sprite collidedSprite) {
        Object bulletOwnerType = getOwnerSprite().getComponent(BulletComponent.class).getBulletOwnerType();

        // check bullet team
        if (bulletOwnerType != Types.PLAYER && collidedSprite.isType(Types.PLAYER) ||
                bulletOwnerType == Types.PLAYER && !collidedSprite.isType(Types.PLAYER)) {
            collidedSprite.getComponentOptional(HpComponent.class)
                    .ifPresent(hpComponent -> hpComponent.lostHp(1));

            getAttachedWorld().removeSprite(getOwnerSprite());
        }
    }

}
