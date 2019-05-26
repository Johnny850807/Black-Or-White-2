package Pacman;

import com.pokewords.framework.commons.Direction;
import com.pokewords.framework.engine.asm.states.multiplayer.Player;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.KeyListenerComponent;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("Duplicates")
public class PlayerKeyListener extends KeyListenerComponent.Listener {

    @Override
    public void onKeyPressed(Sprite sprite, int keyCode) {
        switch (keyCode)
        {
            case KeyEvent.VK_UP:
                move(sprite, Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                move(sprite, Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                move(sprite, Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                move(sprite, Direction.RIGHT);
                break;
        }
    }

    @Override
    public void onKeyReleased(Sprite sprite, int keyCode) {
        switch (keyCode)
        {
            case KeyEvent.VK_UP:
                clearMovement(sprite, Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                clearMovement(sprite, Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                clearMovement(sprite, Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                clearMovement(sprite, Direction.RIGHT);
                break;
        }
    }

    @Override
    public void onKeyClicked(Sprite sprite, int keyCode) {
        super.onKeyClicked(sprite, keyCode);
        if (keyCode == KeyEvent.VK_SPACE) {
            sprite.getComponent(GunComponent.class).shootIfAvailable();
        }
    }

    private void move(Sprite sprite, Direction direction) {
        sprite.getComponent(PacmanComponent.class).move(direction);
    }

    private void clearMovement(Sprite sprite, Direction direction) {
        sprite.getComponent(PacmanComponent.class).clearMovement(direction);
    }


}
