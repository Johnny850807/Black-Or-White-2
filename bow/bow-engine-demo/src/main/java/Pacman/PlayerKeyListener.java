package Pacman;

import com.pokewords.framework.commons.Direction;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.KeyListenerComponent;

import java.awt.event.KeyEvent;

@SuppressWarnings("Duplicates")
public class PlayerKeyListener extends KeyListenerComponent.Listener {

    @Override
    public void onKeyPressed(Sprite sprite, int keyCode) {
        switch (keyCode) {
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
            case KeyEvent.VK_SPACE:
                Direction direction = sprite.getComponent(MovingComponent.class).getLatestDirection();
                sprite.getComponent(GunComponent.class).shootIfAvailable(direction);
        }
    }

    @Override
    public void onKeyReleased(Sprite sprite, int keyCode) {
        switch (keyCode) {
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

    private void move(Sprite sprite, Direction direction) {
        sprite.getComponent(MovingComponent.class).move(direction);

        sprite.trigger("walkLeft");
    }

    private void clearMovement(Sprite sprite, Direction direction) {
        sprite.getComponent(MovingComponent.class).clearMovement(direction);
    }


}
