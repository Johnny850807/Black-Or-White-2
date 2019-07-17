package com.pokewords.components;

import com.pokewords.components.actions.PickComponent;
import com.pokewords.components.actions.ShootComponent;
import com.pokewords.components.actions.UseItemComponent;
import com.pokewords.constants.FsmcEvents;
import com.pokewords.framework.commons.Direction;
import com.pokewords.framework.sprites.components.KeyListenerComponent;

import java.awt.event.KeyEvent;

/**
 * @author johnny850807
 *
 * Key Listener Component that operates the basic hero action.
 */
@SuppressWarnings("Duplicates")
public class HeroKeyListenerComponent extends KeyListenerComponent {
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

    @Override
    public void onKeyClicked(int keyCode) {
        super.onKeyClicked(keyCode);
        switch (keyCode) {
            case KeyEvent.VK_SLASH:
                getOwnerSprite().getComponent(CharacterComponent.class).action(ShootComponent.class);
                break;
            case KeyEvent.VK_PERIOD:
                getOwnerSprite().getComponent(CharacterComponent.class).action(PickComponent.class);
                break;
            case KeyEvent.VK_COMMA:
                getOwnerSprite().getComponent(CharacterComponent.class).action(UseItemComponent.class);
                break;
            case KeyEvent.VK_NUMPAD1:
                getOwnerSprite().getComponent(PackageComponent.class).setCurrentChosenGunIndex(0);
                break;
            case KeyEvent.VK_NUMPAD2:
                getOwnerSprite().getComponent(PackageComponent.class).setCurrentChosenGunIndex(1);
                break;
            case KeyEvent.VK_NUMPAD3:
                getOwnerSprite().getComponent(PackageComponent.class).setCurrentChosenGunIndex(2);
                break;
        }
    }
}