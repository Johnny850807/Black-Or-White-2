package homework;

import com.pokewords.framework.commons.Direction;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.KeyListenerComponent;
import com.pokewords.framework.sprites.factories.SpriteInitializer;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("Duplicates")
public class PlayerKeyListener extends KeyListenerComponent.Listener {
    private Set<Direction> directions = new HashSet<>();
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

    @Override
    public void onKeyClicked(Sprite sprite, int keyCode) {
        super.onKeyClicked(sprite, keyCode);
    }

    private void clearMovement(Direction direction) {
        directions.remove(direction);
    }

    private void move(Direction direction) {
        directions.add(direction);
        directions.remove(direction.getOppositeDirection());
    }

    @Override
    public void onUpdate(Sprite sprite) {
        for (Direction direction : directions) {
            sprite.move(direction.move(12));
        }
    }
}
