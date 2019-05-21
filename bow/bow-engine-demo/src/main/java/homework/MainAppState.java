package homework;

import com.pokewords.framework.engine.asm.EmptyAppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.StringComponent;

import java.awt.*;
import java.util.Random;

@SuppressWarnings("Duplicates")
public class MainAppState extends EmptyAppState {
    private Sprite mousePositionText;

    @Override
    protected void onAppStateCreating(AppStateWorld appStateWorld) {
        getGameWindowsConfigurator().gameSize(800, 600);
        Random random = new Random();
        Sprite ai = createSprite(Types.AI1);
        ai.setPosition(random.nextInt(400)+280, random.nextInt(380));
        Sprite ai2 = createSprite(Types.AI2);
        ai2.setPosition(random.nextInt(400)+300, random.nextInt(380));
        Sprite ai3 = createSprite(Types.AI3);
        ai3.setPosition(random.nextInt(400)+325, random.nextInt(380));

        Sprite player = createSprite(Types.PLAYER);
        player.setPosition(400, 480);

        appStateWorld.spawn(ai);
        appStateWorld.spawn(ai2);
        appStateWorld.spawn(ai3);
        appStateWorld.spawn(player);
        appStateWorld.spawn(mousePositionText = createSprite(Types.MOUSE_POSITION));

        bindMouseMovedAction(this::updateMousePositionText);
    }


    private void updateMousePositionText(Point point) {
        mousePositionText.getComponent(StringComponent.class)
                .setText("(" + point.x + ", " + point.y + ")");
    }
}
