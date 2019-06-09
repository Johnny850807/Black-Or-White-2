package basics;

import com.pokewords.framework.commons.Direction;
import com.pokewords.framework.sprites.Sprite;
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
                    getOwnerSprite().trigger(WalkEvent.walkUp);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (currentDirection != Direction.DOWN) {
                    currentDirection = Direction.DOWN;
                    getOwnerSprite().trigger(WalkEvent.walkDown);
                }
                break;
            case KeyEvent.VK_LEFT:
                if (currentDirection != Direction.LEFT) {
                    currentDirection = Direction.LEFT;
                    getOwnerSprite().trigger(WalkEvent.walkLeft);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (currentDirection != Direction.RIGHT) {
                    currentDirection = Direction.RIGHT;
                    getOwnerSprite().trigger(WalkEvent.walkRight);
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
                    getOwnerSprite().trigger(WalkEvent.halt);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (currentDirection == Direction.DOWN) {
                    currentDirection = Direction.NO_DIRECTION;
                    getOwnerSprite().trigger(WalkEvent.halt);
                }
                break;
            case KeyEvent.VK_LEFT:
                if (currentDirection == Direction.LEFT) {
                    currentDirection = Direction.NO_DIRECTION;
                    getOwnerSprite().trigger(WalkEvent.halt);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (currentDirection == Direction.RIGHT) {
                    currentDirection = Direction.NO_DIRECTION;
                    getOwnerSprite().trigger(WalkEvent.halt);
                }
                break;
        }
    }
}