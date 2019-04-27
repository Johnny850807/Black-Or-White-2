package basics.states;


import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;
import com.pokewords.framework.views.InputManager;
import javafx.scene.input.KeyCode;

import java.awt.event.KeyEvent;

public class MainState extends AppState {
    private InputManager inputManager;
    public MainState(AppStateMachine appStateMachine) {
        super(appStateMachine);
    }

    @Override
    public void onAppStateStart(AppStateWorld world) {
        super.onAppStateStart(world);
        inputManager = iocFactory.inputs();
    }

    public void onAppStateEnter() {

    }

    public void onAppStateExit() {

    }

    public void onAppStateDestroy() {

    }

    public void onUpdate(double tpf) {
        if (inputManager.getButtonBeingHeld(KeyEvent.VK_W))
            moveUp();
        if (inputManager.getButtonBeingHeld(KeyEvent.VK_S))
            moveDown();
        if (inputManager.getButtonBeingHeld(KeyEvent.VK_A))
            moveLeft();
        if (inputManager.getButtonBeingHeld(KeyEvent.VK_D))
            moveRight();
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
