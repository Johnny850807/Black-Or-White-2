package homework;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.KeyListenerComponent;

import java.awt.event.KeyEvent;

@SuppressWarnings("Duplicates")
public class PlayerKeyListener implements KeyListenerComponent.Listener {
    private boolean moving = false;
    private Direction direction;

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    @Override
    public void onKeyPressed(Sprite sprite, int keyCode) {
        switch (keyCode)
        {
            case KeyEvent.VK_W:
                move(Direction.UP);
                break;
            case KeyEvent.VK_S:
                move(Direction.DOWN);
                break;
            case KeyEvent.VK_A:
                move(Direction.LEFT);
                break;
            case KeyEvent.VK_D:
                move(Direction.RIGHT);
                break;
        }
    }

    @Override
    public void onKeyReleased(Sprite sprite, int keyCode) {
        switch (keyCode)
        {
            case KeyEvent.VK_W:
                clearMovement(Direction.UP);
                break;
            case KeyEvent.VK_S:
                clearMovement(Direction.DOWN);
                break;
            case KeyEvent.VK_A:
                clearMovement(Direction.LEFT);
                break;
            case KeyEvent.VK_D:
                clearMovement(Direction.RIGHT);
                break;
        }
    }

    private void clearMovement(Direction direction) {
        if (this.direction == direction) {
            moving = false;
            this.direction = null;
        }
    }

    private void move(Direction direction) {
        moving = true;
        this.direction = direction;
    }

    @Override
    public void onUpdate(Sprite sprite) {
        if (moving) {
            switch (direction) {
                case UP:
                    sprite.moveY(-8);
                    break;
                case DOWN:
                    sprite.moveY(8);
                    break;
                case LEFT:
                    sprite.moveX(-8);
                    break;
                case RIGHT:
                    sprite.moveX(8);
                    break;
            }
        }
    }
}
