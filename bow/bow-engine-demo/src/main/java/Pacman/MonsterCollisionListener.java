package Pacman;

import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.CollisionListenerComponent;

public class MonsterCollisionListener extends CollisionListenerComponent.Listener {
    private long loopTime;
    private long latestRigidCollisionTime;
    private boolean injuryEnabled = true;

    @Override
    public void onCollision(Sprite ownerSprite, Sprite thatSprite, GameEngineFacade gameEngineFacade) { }

    @Override
    public void onUpdate(double timePerFrame, Sprite ownerSprite) {
        loopTime = (loopTime + 1) % Long.MAX_VALUE;

        if ((loopTime - latestRigidCollisionTime) % 28 == 0)
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
            gameEngineFacade.playSoundIfNotPlaying(Types.PACE);
            thatSprite.getComponent(HpComponent.class).lostHp(1);
            latestRigidCollisionTime = loopTime;
            injuryEnabled = false;
        }
    }
}
