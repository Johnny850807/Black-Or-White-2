package basics;

import com.pokewords.framework.commons.Direction;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.KeyListenerComponent;

import java.awt.event.KeyEvent;

public class PlayerKeyListener extends KeyListenerComponent.Listener {
    private Direction currentDirection = Direction.NO_DIRECTION;
    @Override
    public void onKeyPressed(Sprite sprite, int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                currentDirection = Direction.UP;
                sprite.trigger(WalkEvent.walkUp);
                break;
            case KeyEvent.VK_DOWN:
                currentDirection = Direction.DOWN;
                sprite.trigger(WalkEvent.walkDown);
                break;
            case KeyEvent.VK_LEFT:
                currentDirection = Direction.LEFT;
                sprite.trigger(WalkEvent.walkLeft);
                break;
            case KeyEvent.VK_RIGHT:
                currentDirection = Direction.RIGHT;
                sprite.trigger(WalkEvent.walkRight);
                break;
        }
    }

    @Override
    public void onKeyReleased(Sprite sprite, int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                if (currentDirection == Direction.UP) {
                    currentDirection = Direction.NO_DIRECTION;
                    sprite.trigger(WalkEvent.halt);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (currentDirection == Direction.DOWN){
                    currentDirection = Direction.NO_DIRECTION;
                    sprite.trigger(WalkEvent.halt);
                }
                break;
            case KeyEvent.VK_LEFT:
                if (currentDirection == Direction.LEFT){
                    currentDirection = Direction.NO_DIRECTION;
                    sprite.trigger(WalkEvent.halt);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (currentDirection == Direction.RIGHT){
                    currentDirection = Direction.NO_DIRECTION;
                    sprite.trigger(WalkEvent.halt);
                }
                break;
        }
    }
}