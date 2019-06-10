package Pacman;

import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.CollisionListenerComponent;
import com.pokewords.framework.sprites.components.ImageComponent;

import java.lang.reflect.Type;

public class PacmanCollisionListenerComponent extends CollisionListenerComponent {
    private long loopTime;
    private long latestRigidCollisionLoop = 0;

    @Override
    public void onUpdate(double timePerFrame) {
        loopTime = (loopTime + 1) % Long.MAX_VALUE;

        if ((loopTime - latestRigidCollisionLoop) % 28 == 0)
            getOwnerSprite().getComponent(ImageComponent.class).setImage("images/smile.png");
    }

    @Override
    public void onRigidCollisionWithSprite(Sprite collidedSprite) {
        if (collidedSprite.anyType(Types.AI1, Types.AI2, Types.AI3))
        {
            getOwnerSprite().getComponent(ImageComponent.class).setImage("images/cry.png");
            latestRigidCollisionLoop = loopTime;
        }
    }
}
