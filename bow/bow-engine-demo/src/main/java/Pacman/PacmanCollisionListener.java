package Pacman;

import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.CollisionListenerComponent;
import com.pokewords.framework.sprites.components.ImageComponent;

public class PacmanCollisionListener extends CollisionListenerComponent.Listener {
    private long latestRigidCollisionTime;

    @Override
    public void onCollision(Sprite ownerSprite, Sprite thatSprite, GameEngineFacade gameEngineFacade) { }

    @Override
    public void onUpdate(double timePerFrame, Sprite ownerSprite) {
        if (System.currentTimeMillis() - latestRigidCollisionTime > 650)
            ownerSprite.getComponent(ImageComponent.class).setImage("images/smile.png");
    }

    @Override
    public void onRigidCollisionEvent(Sprite ownerSprite, GameEngineFacade gameEngineFacade) {
        gameEngineFacade.playSoundIfNotPlaying(Types.PACE);
        ownerSprite.getComponent(ImageComponent.class).setImage("images/cry.png");
        latestRigidCollisionTime = System.currentTimeMillis();
    }

    @Override
    public void onRigidCollisionToSprite(Sprite ownerSprite, Sprite thatSprite, GameEngineFacade gameEngineFacade) { }
}
