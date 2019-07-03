package basics;


import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.engine.asm.states.EmptyAppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.engine.gameworlds.ContainerAppStateWorld;

public class MainAppState extends EmptyAppState {

    enum Sprites {
        CHARACTER
    }

    @Override
    protected AppStateWorld onCreateAppStateWorld(GameEngineFacade gameEngineFacade) {
        return new ContainerAppStateWorld(this, 800, 600);
    }

    @Override
    protected void onAppStateCreating(AppStateWorld world) {
        getGameWindowsConfigurator().gameSize(800, 600);
        world.spawn(createSprite(Sprites.CHARACTER));
    }
}
