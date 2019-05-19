package basics.states;


import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.components.ImageComponent;
import com.pokewords.framework.sprites.components.frames.ImageFrame;

public class MainAppState extends AppState {
    enum Types {
        SMILE
    }

    @Override
    protected void onAppStateCreating(AppStateWorld world) {
        getSpriteInitializer().declare(Types.SMILE)
                .with(new ImageComponent(new ImageFrame(0, 2, 50, 50, "images/smile.png", true)))
                .position(getGameWindowDefinition().center())
                .commit();

        getAppStateWorld().spawn(createSprite(Types.SMILE));


    }


    @Override
    public void onAppStateEntering() {
    }

    @Override
    public void onAppStateExiting() {
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
