package com.pokewords.components;

import com.pokewords.constants.FsmcEvents;
import com.pokewords.framework.commons.Direction;
import com.pokewords.framework.sprites.components.KeyListenerComponent;

import java.awt.event.KeyEvent;

/**
 * @author johnny850807
 *
 * Component that listens ↑↓←→ keys to trigger the walkUp, walkDown, walkLeft, walkRight events
 * and trigger halt event while releasing key to the owner sprite
 */
@SuppressWarnings("Duplicates")
public class MovingKeyListenerComponent extends KeyListenerComponent {
    private Direction currentDirection = Direction.NO_DIRECTION;

    @Override
    public void onKeyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                move(Direction.UP, FsmcEvents.walkUp);
                break;
            case KeyEvent.VK_DOWN:
                move(Direction.DOWN, FsmcEvents.walkDown);
                break;
            case KeyEvent.VK_LEFT:
                move(Direction.LEFT, FsmcEvents.walkLeft);
                break;
            case KeyEvent.VK_RIGHT:
                move(Direction.RIGHT, FsmcEvents.walkRight);
                break;
        }
    }

    private void move(Direction direction, FsmcEvents moveEvent) {
        if (currentDirection != direction) {
            currentDirection = direction;
            getOwnerSprite().trigger(moveEvent);
        }
    }

    @Override
    public void onKeyReleased(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                move(Direction.UP, FsmcEvents.halt);
                break;
            case KeyEvent.VK_DOWN:
                move(Direction.DOWN, FsmcEvents.halt);
                break;
            case KeyEvent.VK_LEFT:
                move(Direction.LEFT, FsmcEvents.halt);
                break;
            case KeyEvent.VK_RIGHT:
                move(Direction.RIGHT, FsmcEvents.halt);
                break;
        }
    }
}