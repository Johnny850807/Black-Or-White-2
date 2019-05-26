package Pacman;

import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.CollisionListenerComponent;
import com.pokewords.framework.sprites.components.ImageComponent;

public class MonsterCollisionListener extends CollisionListenerComponent.Listener {
    private long latestRigidCollisionTime;
    private boolean injuryEnabled = true;

    @Override
    public void onCollision(Sprite ownerSprite, Sprite thatSprite, GameEngineFacade gameEngineFacade) { }

    @Override
    public void onUpdate(double timePerFrame, Sprite ownerSprite) {
        if (System.currentTimeMillis() - latestRigidCollisionTime > 650)
            injuryEnabled = true;
    }

    @Override
    public void onRigidCollisionEvent(Sprite ownerSprite, GameEngineFacade gameEngineFacade) {
        latestRigidCollisionTime = System.currentTimeMillis();
    }

    @Override
    public void onRigidCollisionToSprite(Sprite ownerSprite, Sprite thatSprite, GameEngineFacade gameEngineFacade) {
        if (thatSprite.isType(Types.PLAYER) && injuryEnabled)
        {
            thatSprite.getComponent(HpComponent.class).lostHp(1);
            injuryEnabled = false;
        }
    }
}
