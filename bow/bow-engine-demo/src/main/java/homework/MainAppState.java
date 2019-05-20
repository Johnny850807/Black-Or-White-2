package homework;

import com.pokewords.framework.engine.asm.EmptyAppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.Sprite;

import java.awt.event.KeyEvent;
import java.util.Random;

@SuppressWarnings("Duplicates")
public class MainAppState extends EmptyAppState {
    private Sprite player;
    private boolean moving = false;
    private Direction direction;

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    @Override
    protected void onAppStateCreating(AppStateWorld appStateWorld) {
        getGameWindowsConfigurator().size(800, 600);
        Random random = new Random();
        Sprite ai = createSprite(Types.AI1);
        ai.setPosition(random.nextInt(400)+280, random.nextInt(380));
        Sprite ai2 = createSprite(Types.AI2);
        ai2.setPosition(random.nextInt(400)+300, random.nextInt(380));
        Sprite ai3 = createSprite(Types.AI3);
        ai3.setPosition(random.nextInt(400)+325, random.nextInt(380));

        player = createSprite(Types.PLAYER);
        player.setPosition(400, 480);

        appStateWorld.spawn(ai);
        appStateWorld.spawn(ai2);
        appStateWorld.spawn(ai3);
        appStateWorld.spawn(player);

        bindKeyPressedAction(KeyEvent.VK_W, () -> move(Direction.UP));
        bindKeyPressedAction(KeyEvent.VK_S, () -> move(Direction.DOWN));
        bindKeyPressedAction(KeyEvent.VK_A, () -> move(Direction.LEFT));
        bindKeyPressedAction(KeyEvent.VK_D, () -> move(Direction.RIGHT));

        bindKeyReleasedAction(KeyEvent.VK_W, () -> clearMovement(Direction.UP));
        bindKeyReleasedAction(KeyEvent.VK_S, () -> clearMovement(Direction.DOWN));
        bindKeyReleasedAction(KeyEvent.VK_A, () -> clearMovement(Direction.LEFT));
        bindKeyReleasedAction(KeyEvent.VK_D, () -> clearMovement(Direction.RIGHT));
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
        if (moving) {
            switch (direction) {
                case UP:
                    player.moveY(-8);
                    break;
                case DOWN:
                    player.moveY(8);
                    break;
                case LEFT:
                    player.moveX(-8);
                    break;
                case RIGHT:
                    player.moveX(8);
                    break;
            }
        }
    }

}
