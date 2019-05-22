package com.pokewords.framework.commons;

import java.awt.*;

public enum Direction {
    UP, DOWN, LEFT, RIGHT, LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN;


    public Direction getOppositeDirection() {
        switch (this) {
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

    public Point move(int speed) {
        switch (this) {
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
