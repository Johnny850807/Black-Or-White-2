package Pacman;

import com.pokewords.framework.commons.Direction;
import com.pokewords.framework.sprites.components.CloneableComponent;

import java.util.Random;

public class AiComponent extends CloneableComponent {
    private final static Random random = new Random();
    private final static Direction[] directions = Direction.allDirections();
    private long loopTime = 0;
    private int nextDecisionInterval = 0;
    private Direction currentDirection = directions[random.nextInt(directions.length)];

    @Override
    public void onUpdate(double timePerFrame) {
        if (loopTime++ == nextDecisionInterval)
        {
            currentDirection = directions[random.nextInt(directions.length)];
            getOwnerSprite().getComponent(MovingComponent.class).move(currentDirection);
            nextDecisionInterval = random.nextInt(55) + 10;
            loopTime = 0;
        }
        else if (random.nextInt(100) > 95)
            getOwnerSprite().getComponent(MovingComponent.class).clearMovement(currentDirection);

        if (random.nextInt(1000) > 990) {
            Direction direction = getOwnerSprite().getComponent(MovingComponent.class).getLatestDirection();
            getOwnerSprite().getComponentOptional(GunComponent.class)
                    .ifPresent(gun -> gun.shootIfAvailable(direction));
        }
    }

    @Override
    public AiComponent clone() {
        AiComponent clone = (AiComponent) super.clone();
        clone.currentDirection = directions[random.nextInt(directions.length)];
        return clone;
    }
}
