package Pacman;

import com.pokewords.framework.commons.Direction;
import com.pokewords.framework.sprites.components.CloneableComponent;

public class MovingComponent extends CloneableComponent {
    private Direction latestDirection = Direction.UP;
    private Direction direction = Direction.NO_DIRECTION;
    private int speed;

    public MovingComponent(int speed) {
        this.speed = speed;
    }

    @Override
    public void onUpdate(double timePerFrame) {
        if (direction != null)
            getOwnerSprite().move(direction.getMovement(speed));
    }

    public void move(Direction direction) {
        this.direction = this.direction.addDirection(direction);
        this.latestDirection = this.direction;
    }

    public void clearMovement(Direction direction)  {
        if (this.direction != Direction.NO_DIRECTION)
            this.latestDirection = this.direction;
        this.direction = this.direction.clearDirection(direction);
    }

    public Direction getDirection() {
        return direction;
    }

    public Direction getLatestDirection() {
        return latestDirection;
    }
}
