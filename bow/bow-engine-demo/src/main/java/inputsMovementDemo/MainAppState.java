package inputsMovementDemo;


import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.ImageComponent;
import com.pokewords.framework.sprites.components.frames.ImageFrame;

import java.awt.event.KeyEvent;

public class MainAppState extends AppState {
    private Sprite faceSprite;
    private boolean buttonHolding = false;
    private Direction direction;

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    enum Types {
        SMILE
    }

    @Override
    protected void onAppStateCreating(AppStateWorld world) {
        faceSprite = createSprite(Types.SMILE);
        getAppStateWorld().spawn(faceSprite);

        bindKeyPressedAction(KeyEvent.VK_W, () -> move(Direction.UP));
        bindKeyPressedAction(KeyEvent.VK_S, () -> move(Direction.DOWN));
        bindKeyPressedAction(KeyEvent.VK_A, () -> move(Direction.LEFT));
        bindKeyPressedAction(KeyEvent.VK_D, () -> move(Direction.RIGHT));
        bindMouseClickedAction((position) -> faceSprite.setPosition(position));

        bindKeyReleasedAction(KeyEvent.VK_W, this::clearMovement);
        bindKeyReleasedAction(KeyEvent.VK_S, this::clearMovement);
        bindKeyReleasedAction(KeyEvent.VK_A, this::clearMovement);
        bindKeyReleasedAction(KeyEvent.VK_D, this::clearMovement);
    }

    private void clearMovement() {
        buttonHolding = false;
        direction = null;
    }

    private void move(Direction direction) {
        buttonHolding = true;
        this.direction = direction;
    }

    @Override
    public void onAppStateUpdating(double timePerFrame) {
        if (buttonHolding)
        {
            switch (direction)
            {
                case UP:
                    faceSprite.moveY(-1);
                    break;
                case DOWN:
                    faceSprite.moveY(1);
                    break;
                case LEFT:
                    faceSprite.moveX(-1);
                    break;
                case RIGHT:
                    faceSprite.moveX(1);
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
