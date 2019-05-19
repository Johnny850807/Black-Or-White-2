package basics;


import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;

public class MainAppState extends AppState {

    enum Sprites {
        CHARACTER
    }
    @Override
    protected void onAppStateCreating(AppStateWorld world) {

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
