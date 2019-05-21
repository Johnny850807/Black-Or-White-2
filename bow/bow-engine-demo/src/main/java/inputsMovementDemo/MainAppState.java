package inputsMovementDemo;


import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.Sprite;

import java.awt.event.KeyEvent;

@SuppressWarnings("Duplicates")
public class MainAppState extends AppState {
    private Sprite face;
    private Sprite dinosaur;
    private boolean moving = false;
    private Direction direction;

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    @SuppressWarnings("Duplicates")
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

    private void onKeyReleased(int keyCode) {
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
    public void onAppStateUpdating(double timePerFrame) {
        if (moving)
        {
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
