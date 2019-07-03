package com.pokewords.components;

import com.pokewords.constants.FsmcEvents;
import com.pokewords.framework.commons.Direction;
import com.pokewords.framework.sprites.components.KeyListenerComponent;

import java.awt.event.KeyEvent;

public class PlayerKeyListenerComponent extends KeyListenerComponent {
    private Direction currentDirection = Direction.NO_DIRECTION;

    @Override
    public void onKeyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                if (currentDirection != Direction.UP)
                {
                    currentDirection = Direction.UP;
                    getOwnerSprite().trigger(FsmcEvents.walkUp);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (currentDirection != Direction.DOWN) {
                    currentDirection = Direction.DOWN;
                    getOwnerSprite().trigger(FsmcEvents.walkDown);
                }
                break;
            case KeyEvent.VK_LEFT:
                if (currentDirection != Direction.LEFT) {
                    currentDirection = Direction.LEFT;
                    getOwnerSprite().trigger(FsmcEvents.walkLeft);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (currentDirection != Direction.RIGHT) {
                    currentDirection = Direction.RIGHT;
                    getOwnerSprite().trigger(FsmcEvents.walkRight);
                }
                break;
        }
    }

    @Override
    public void onKeyReleased(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                if (currentDirection == Direction.UP) {
                    currentDirection = Direction.NO_DIRECTION;
                    getOwnerSprite().trigger(FsmcEvents.halt);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (currentDirection == Direction.DOWN) {
                    currentDirection = Direction.NO_DIRECTION;
                    getOwnerSprite().trigger(FsmcEvents.halt);
                }
                break;
            case KeyEvent.VK_LEFT:
                if (currentDirection == Direction.LEFT) {
                    currentDirection = Direction.NO_DIRECTION;
                    getOwnerSprite().trigger(FsmcEvents.halt);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (currentDirection == Direction.RIGHT) {
                    currentDirection = Direction.NO_DIRECTION;
                    getOwnerSprite().trigger(FsmcEvents.halt);
                }
                break;
        }
    }
}