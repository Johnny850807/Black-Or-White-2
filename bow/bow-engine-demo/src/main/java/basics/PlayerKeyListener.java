package basics;

import com.pokewords.framework.commons.Direction;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.KeyListenerComponent;

import java.awt.event.KeyEvent;

public class PlayerKeyListener extends KeyListenerComponent.Listener {

    @Override
    public void onKeyPressed(Sprite sprite, int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                sprite.trigger(WalkEvent.walkUp);
                break;
            case KeyEvent.VK_DOWN:
                sprite.trigger(WalkEvent.walkDown);
                break;
            case KeyEvent.VK_LEFT:
                sprite.trigger(WalkEvent.walkLeft);
                break;
            case KeyEvent.VK_RIGHT:
                sprite.trigger(WalkEvent.walkRight);
                break;
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

    private void clearMovement(Sprite sprite, Direction direction) {
    }


}