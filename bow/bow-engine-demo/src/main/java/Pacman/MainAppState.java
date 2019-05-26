package Pacman;

import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.engine.asm.states.EmptyAppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.engine.gameworlds.CollisionHandler;
import com.pokewords.framework.engine.gameworlds.ContainerAppStateWorld;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.StringComponent;

import java.awt.*;

@SuppressWarnings("Duplicates")
public class MainAppState extends EmptyAppState {
    private Sprite mousePositionText;

    @Override
    protected AppStateWorld onCreateAppStateWorld(GameEngineFacade gameEngineFacade) {
        return new ContainerAppStateWorld(this);
    }

    @Override
    protected void onAppStateCreating(AppStateWorld appStateWorld) {
        getGameWindowsConfigurator().gameSize(800, 600);

        appStateWorld.spawn(createSprite(Types.AI1, 62, 229));
        appStateWorld.spawn(createSprite(Types.AI2, 215, 70));
        appStateWorld.spawn(createSprite(Types.AI3, 493, 64));
        appStateWorld.spawn(createSprite(Types.AI1, 695, 210));
        appStateWorld.spawn(createSprite(Types.AI2, 685, 431));
        appStateWorld.spawn(createSprite(Types.AI3, 433, 528));
        appStateWorld.spawn(createSprite(Types.AI1, 46, 47));
        appStateWorld.spawn(createSprite(Types.PLAYER, 371, 276));
        appStateWorld.spawn(mousePositionText = createSprite(Types.MOUSE_POSITION));

        bindMouseMovedAction(this::updateMousePositionText);

    }

    private void updateMousePositionText(Point point) {
        mousePositionText.getComponent(StringComponent.class)
                .setText("(" + point.x + ", " + point.y + ")");
    }
}
