package Pacman;

import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.CollisionListenerComponent;

public class BulletCollisionListener extends CollisionListenerComponent.Listener {
    @Override
    public void onCollision(Sprite ownerSprite, Sprite thatSprite, GameEngineFacade gameEngineFacade) {
        if (!thatSprite.isType(Types.PLAYER))
        {
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
    public void onRigidCollisionToSprite(Sprite ownerSprite, Sprite thatSprite, GameEngineFacade gameEngineFacade) {


    }
}
