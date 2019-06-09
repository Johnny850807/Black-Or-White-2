package Pacman;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.CollisionListenerComponent;
import com.pokewords.framework.sprites.components.HpComponent;

public class MonsterCollisionListenerComponent extends CollisionListenerComponent {
    private long loopTime;
    private long latestRigidCollisionTime;
    private boolean injuryEnabled = true;

    @Override
    public void onUpdate(double timePerFrame) {
        loopTime = (loopTime + 1) % Long.MAX_VALUE;

        if ((loopTime - latestRigidCollisionTime) >= 28)
            injuryEnabled = true;
    }


    @Override
    public void onRigidCollisionWithSprite(Sprite collidedSprite) {
        if (collidedSprite.isType(Types.PLAYER) && injuryEnabled)
        {
            gameEngineFacade.playSoundIfNotPlaying(Types.PACE);
            collidedSprite.getComponent(HpComponent.class).lostHp(1);
            latestRigidCollisionTime = loopTime;
            injuryEnabled = false;
        }
    }
}
