package Pacman;

import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.CollisionListenerComponent;
import com.pokewords.framework.sprites.components.ImageComponent;

public class PacmanCollisionListener extends CollisionListenerComponent.Listener {
    private long loopTime;
    private long latestRigidCollisionLoop = 0;

    @Override
    public void onCollision(Sprite ownerSprite, Sprite thatSprite, GameEngineFacade gameEngineFacade) { }

    @Override
    public void onUpdate(double timePerFrame, Sprite ownerSprite) {
        loopTime = (loopTime + 1) % Long.MAX_VALUE;

        if ((loopTime - latestRigidCollisionLoop) % 28 == 0)
            ownerSprite.getComponent(ImageComponent.class).setImage("images/smile.png");
    }

    @Override
    public void onRigidCollisionEvent(Sprite ownerSprite, GameEngineFacade gameEngineFacade) {
        ownerSprite.getComponent(ImageComponent.class).setImage("images/cry.png");
        latestRigidCollisionLoop = loopTime;
    }

    @Override
    public void onRigidCollisionToSprite(Sprite ownerSprite, Sprite thatSprite, GameEngineFacade gameEngineFacade) { }
}
