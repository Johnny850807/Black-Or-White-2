package Pacman;

import com.pokewords.framework.commons.Direction;
import com.pokewords.framework.sprites.components.CloneableComponent;

import java.util.Random;

public class AiComponent extends CloneableComponent {
    private final static Random random = new Random();
    private long loopTime = 0;
    private Direction[] directions = Direction.values();
    private Direction currentDirection = directions[random.nextInt(directions.length)];

    @Override
    public void onUpdate(double timePerFrame) {
        loopTime  = (loopTime + 1) % Integer.MAX_VALUE;

        if (loopTime % 80 == 0)
            currentDirection = directions[random.nextInt(directions.length)];

        if (random.nextInt(100) > 75)
        {
            int speed = random.nextInt(11) + 4;
            getOwnerSprite().move(currentDirection.move(speed));
        }
    }
}
