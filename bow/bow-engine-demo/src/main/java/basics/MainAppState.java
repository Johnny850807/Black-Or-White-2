package basics;


import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.parsing.*;

public class MainAppState extends AppState {

    enum Sprites {
        CHARACTER
    }
    @Override
    protected void onAppStateCreating(AppStateWorld world) {
        getSpriteInitializer().declare(Sprites.CHARACTER)
                .with("scripts/character.bow")
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
