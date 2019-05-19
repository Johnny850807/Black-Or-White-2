package basics.states;


import basics.weaver.MovementTransitionsWeaverNode;
import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;

public class MainAppState extends AppState {

    enum Sprites {
        CHARACTER
    }
    @Override
    protected void onAppStateCreating(AppStateWorld world) {
        getSpriteInitializer().declare(Sprites.CHARACTER)
                        .position(getGameWindowDefinition().center())
                        .with("scripts/character.bow")
                        .weaver(new MovementTransitionsWeaverNode())
                        .commit();
    }


    @Override
    public void onAppStateEntering() {

    }

    @Override
    public void onAppStateExiting() {
        getAppStateWorld().clearSprites();
    }

    @Override
    protected void onAppStateDestroying() {

    }


    @Override
    public void onAppStateUpdating(double timePerFrame) {
    }

    private void moveUp(){

    }
    private void moveLeft(){

    }
    private void moveDown(){

    }
    private void moveRight(){

    }
}
