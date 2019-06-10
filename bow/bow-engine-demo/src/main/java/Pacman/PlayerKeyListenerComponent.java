package Pacman;

import com.pokewords.framework.commons.Direction;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.KeyListenerComponent;

import java.awt.event.KeyEvent;

@SuppressWarnings("Duplicates")
public class PlayerKeyListenerComponent extends KeyListenerComponent {

    @Override
    public void onKeyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                move(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                move(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                move(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                move(Direction.RIGHT);
                break;
            case KeyEvent.VK_SPACE:
                Direction direction = getOwnerSprite().getComponent(MovingComponent.class).getLatestDirection();
                getOwnerSprite().getComponent(GunComponent.class).shootIfAvailable(direction);
        }
    }

    @Override
    public void onKeyReleased(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                clearMovement(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                clearMovement(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                clearMovement(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                clearMovement(Direction.RIGHT);
                break;
        }
    }

    private void move(Direction direction) {
        getOwnerSprite().getComponent(MovingComponent.class).move(direction);
    }

    private void clearMovement(Direction direction) {
        getOwnerSprite().getComponent(MovingComponent.class).clearMovement(direction);
    }


}
