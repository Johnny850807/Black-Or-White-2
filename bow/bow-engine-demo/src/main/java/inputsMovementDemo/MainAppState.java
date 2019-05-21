package inputsMovementDemo;


import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.Sprite;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class MainAppState extends AppState {
    private Sprite face;
    private Sprite dinosaur;
    private Set<Direction> directions = new HashSet<>();

    enum Direction {
        UP, DOWN, LEFT, RIGHT, LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN
    }

    @Override
    protected void onAppStateCreating(AppStateWorld world) {
        face = createSprite(Types.SMILE);
        dinosaur = createSprite(Types.DINOSAUR);
        getAppStateWorld().spawn(dinosaur);
        getAppStateWorld().spawn(face);

        bindKeyPressedAction(this::onKeyPressed);
        bindKeyReleasedAction(this::onKeyReleased);

        bindMouseClickedAction((position) -> face.setPosition(position));
        bindMouseDraggedAction((position) -> {
            face.setPosition(position);
            dinosaur.setPosition(-200, 0);
        });
        bindMouseReleasedAction((position) -> dinosaur.setPosition(position));
        bindMouseMovedAction((position) -> dinosaur.setPosition(position));
    }

    private void onKeyPressed(int keyCode) {
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

    private void move(Direction direction) {
        if (direction == Direction.UP)
            directions.remove(Direction.DOWN);
        else if (direction == Direction.DOWN)
            directions.remove(Direction.UP);
        else if (direction == Direction.RIGHT)
            directions.remove(Direction.LEFT);
        else
            directions.remove(Direction.RIGHT);
        directions.add(direction);
    }

    private void onKeyReleased(int keyCode) {
        switch (keyCode)
        {
            case KeyEvent.VK_W:
                directions.remove(Direction.UP);
                break;
            case KeyEvent.VK_S:
                directions.remove(Direction.DOWN);
                break;
            case KeyEvent.VK_A:
                directions.remove(Direction.LEFT);
                break;
            case KeyEvent.VK_D:
                directions.remove(Direction.RIGHT);
                break;
        }
    }

    @Override
    public void onAppStateUpdating(double timePerFrame) {
        for (Direction direction : directions) {
            switch (direction)
            {
                case UP:
                    face.moveY(-8);
                    break;
                case DOWN:
                    face.moveY(8);
                    break;
                case LEFT:
                    face.moveX(-8);
                    break;
                case RIGHT:
                    face.moveX(8);
                    break;
            }
        }
    }

    @Override
    public void onAppStateEntering() { }

    @Override
    public void onAppStateExiting() { }

    @Override
    protected void onAppStateDestroying() {
    }

}
