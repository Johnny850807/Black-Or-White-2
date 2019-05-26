package com.pokewords.framework.commons;

import java.awt.*;

public enum Direction {
    NO_DIRECTION, UP, DOWN, LEFT, RIGHT, LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN;

    /**
     * @return all directions but 'NO_DIRECTION'
     */
    public static Direction[] allDirections() {
        return new Direction[] {UP, DOWN, LEFT, RIGHT, LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN};
    }

    public Direction getOppositeDirection() {
        switch (this) {
            case NO_DIRECTION:
                return NO_DIRECTION;
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            case LEFT_UP:
                return RIGHT_DOWN;
            case LEFT_DOWN:
                return RIGHT_UP;
            case RIGHT_UP:
                return LEFT_DOWN;
            case RIGHT_DOWN:
                return LEFT_UP;
            default:
                throw new Error("Not reached.");
        }
    }

    @SuppressWarnings("Duplicates")
    public Direction addDirection(Direction direction) {
        switch (this) {
            case NO_DIRECTION:
                return direction;
            case UP:
                switch (direction)
                {
                    case RIGHT: return RIGHT_UP;
                    case LEFT: return LEFT_UP;
                    default:
                        return direction;
                }
            case DOWN:
                switch (direction)
                {
                    case RIGHT: return RIGHT_DOWN;
                    case LEFT: return LEFT_DOWN;
                    default:
                        return direction;
                }
            case LEFT:
                switch (direction)
                {
                    case UP: return LEFT_UP;
                    case DOWN: return LEFT_DOWN;
                    default:
                        return direction;
                }
            case RIGHT:
                switch (direction)
                {
                    case UP: return RIGHT_UP;
                    case DOWN: return RIGHT_DOWN;
                    default:
                        return direction;
                }
            case LEFT_UP:
            case LEFT_DOWN:
            case RIGHT_UP:
            case RIGHT_DOWN:
                return direction;
            default:
                throw new Error("Not reached.");
        }
    }

    @SuppressWarnings("Duplicates")
    public Direction clearDirection(Direction direction) {
        switch (this) {
            case NO_DIRECTION:
                return NO_DIRECTION;
            case LEFT_UP:
                if (direction == LEFT)
                    return UP;
                if (direction == UP)
                    return LEFT;
            case LEFT_DOWN:
                if (direction == LEFT)
                    return DOWN;
                if (direction == DOWN)
                    return LEFT;
            case RIGHT_UP:
                if (direction == RIGHT)
                    return UP;
                if (direction == UP)
                    return RIGHT;
            case RIGHT_DOWN:
                if (direction == RIGHT)
                    return DOWN;
                if (direction == DOWN)
                    return RIGHT;
            case UP:
            case DOWN:
            case LEFT:
            case RIGHT:
                if (this == direction)
                    return Direction.NO_DIRECTION;
                return this;
            default:
                throw new Error("Not reached.");
        }
    }

    public Point move(int speed) {
        switch (this) {
            case NO_DIRECTION:
                return new Point(0, 0);
            case UP:
                return new Point(0, -1*speed);
            case DOWN:
                return new Point(0, speed);
            case LEFT:
                return new Point(-1*speed, 0);
            case RIGHT:
                return new Point(speed, 0);
            case LEFT_UP:
                return new Point(-1*speed, -1*speed);
            case LEFT_DOWN:
                return new Point(-1*speed, speed);
            case RIGHT_UP:
                return new Point(speed, -1*speed);
            case RIGHT_DOWN:
                return new Point(speed, speed);
            default:
                throw new Error("Not reached.");
        }
    }
}
