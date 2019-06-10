package com.pokewords.framework.commons;

import java.awt.*;

/**
 * A useful direction enum, it supports composite direction.
 * @author johnny850807 (waterball)
 */
public enum Direction {
    NO_DIRECTION, UP, DOWN, LEFT, RIGHT, LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN;

    private static final Direction[] ALL_DIRECTIONS = new Direction[] {UP, DOWN, LEFT, RIGHT, LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN};
    private static final Direction[] COMPOSITE_DIRECTIONS = new Direction[] {LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN};
    private static final Direction[] ATOMIC_DIRECTIONS = new Direction[] {UP, DOWN, LEFT, RIGHT};

    /**
     * @return all directions but 'NO_DIRECTION', which are [UP, DOWN, LEFT, RIGHT, LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN]
     */
    public static Direction[] allDirections() {
        return ALL_DIRECTIONS;
    }

    /**
     * @return [LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN]
     */
    public static Direction[] allCompositeDirections() {
        return COMPOSITE_DIRECTIONS;
    }

    /**
     * @return [UP, DOWN, LEFT, RIGHT]
     */
    public static Direction[] allAtomicDirections() {
        return ATOMIC_DIRECTIONS;
    }

    /**
     * @return the opposite direction
     */
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

    /**
     * @return the direction after added another direction.
     * If two directions are compatible, then it will return a composite direction.
     */
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
                switch (direction)
                {
                    case LEFT:
                    case UP:
                        return this;
                    default:
                        return direction;
                }
            case LEFT_DOWN:
                switch (direction)
                {
                    case LEFT:
                    case DOWN:
                        return this;
                    default:
                        return direction;
                }
            case RIGHT_UP:
                switch (direction)
                {
                    case RIGHT:
                    case UP:
                        return this;
                    default:
                        return direction;
                }
            case RIGHT_DOWN:
                switch (direction)
                {
                    case RIGHT:
                    case DOWN:
                        return this;
                    default:
                        return direction;
                }
            default:
                throw new Error("Not reached.");
        }
    }

    /**
     * @return Clear a direction from the current direction.
     */
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

    /**
     * @param speed moving speed
     * @return the movement point according to the Direction
     */
    public Point getMovement(int speed) {
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

    @SuppressWarnings({"ConstantConditions", "Duplicates"})
    public static Direction getDirectionFromOnePointToAnother(Point fromPoint, Point toPoint) {
        Point difference = new Point(toPoint.x - fromPoint.x, toPoint.y - fromPoint.y);
        int x = difference.x;
        int y = difference.y;

        if (x == 0 && y == 0)
            return NO_DIRECTION;

        if (x > 0 && y > 0)
            return RIGHT_DOWN;
        if (x > 0 && y == 0)
            return RIGHT;
        if (x > 0 && y < 0)
            return RIGHT_UP;
        if (x < 0 && y > 0)
            return LEFT_DOWN;
        if (x < 0 && y == 0)
            return LEFT;
        if (x < 0 && y < 0)
            return LEFT_UP;
        if (x == 0 && y < 0)
            return UP;
        if (x == 0 && y > 0)
            return DOWN;

        throw new InternalError("All possible conditions are listed above, this line should not be reached.");
    }

    /**
     * The difference between this method and getDirectionFromOnePointToAnother(), is that this method only
     * return the atomic direction (left, up, right, down) by looking up the primary direction when it's a composite direction.
     */
    @SuppressWarnings({"ConstantConditions", "Duplicates"})
    public static Direction getAtomicDirectionFromOnePointToAnother(Point fromPoint, Point toPoint) {
        Point difference = new Point(toPoint.x - fromPoint.x, toPoint.y - fromPoint.y);
        int x = difference.x;
        int y = difference.y;

        if (x == 0 && y == 0)
            return NO_DIRECTION;

        if (x > 0 && y > 0)
            return x > y ? RIGHT : DOWN;
        if (x > 0 && y == 0)
            return RIGHT;
        if (x > 0 && y < 0)
            return x > Math.abs(y) ? RIGHT : UP;
        if (x < 0 && y > 0)
            return Math.abs(x) > y ? LEFT : DOWN;
        if (x < 0 && y == 0)
            return LEFT;
        if (x < 0 && y < 0)
            return Math.abs(x) > Math.abs(y) ? LEFT : UP;
        if (x == 0 && y < 0)
            return UP;
        if (x == 0 && y > 0)
            return DOWN;

        throw new InternalError("All possible conditions are listed above, this line should not be reached.");
    }
}
