package Pacman;

import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.CollisionListenerComponent;

public class BulletCollisionListener extends CollisionListenerComponent.Listener {
    @Override
    public void onCollisionWithSprite(Sprite ownerSprite, Sprite thatSprite, GameEngineFacade gameEngineFacade) {
        Object bulletOwnerType = ownerSprite.getComponent(BulletComponent.class).getBulletOwnerType();

        // check bullet team
        if (bulletOwnerType != Types.PLAYER && thatSprite.isType(Types.PLAYER) ||
                bulletOwnerType == Types.PLAYER && !thatSprite.isType(Types.PLAYER)) {
            thatSprite.getComponentOptional(HpComponent.class)
                    .ifPresent(hpComponent -> hpComponent.lostHp(1));

            assert ownerSprite.getWorld() != null;
            ownerSprite.getWorld().removeSprite(ownerSprite);
        }
    }

    @Override
    public void onRigidCollisionEvent(Sprite ownerSprite, GameEngineFacade gameEngineFacade) {

    }

    @Override
    public void onRigidCollisionWithSprite(Sprite ownerSprite, Sprite thatSprite, GameEngineFacade gameEngineFacade) {


    }
}
