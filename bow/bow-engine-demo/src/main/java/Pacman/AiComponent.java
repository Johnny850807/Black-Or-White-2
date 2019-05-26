package Pacman;

import com.pokewords.framework.commons.Direction;
import com.pokewords.framework.sprites.components.CloneableComponent;

import java.util.Random;

public class AiComponent extends CloneableComponent {
    private final static Random random = new Random();
    private long latestDirectionChangedTime = System.currentTimeMillis();
    private Direction[] directions = Direction.values();
    private Direction currentDirection = directions[random.nextInt(directions.length)];

    @Override
    public void onUpdate(double timePerFrame) {
        if (System.currentTimeMillis() - latestDirectionChangedTime > 1500)
        {
            currentDirection = directions[random.nextInt(directions.length)];
            latestDirectionChangedTime = System.currentTimeMillis();
        }

        if (random.nextInt(100) > 75)
        {
            int speed = random.nextInt(11) + 4;
            getOwnerSprite().move(currentDirection.move(speed));
        }
    }
}
