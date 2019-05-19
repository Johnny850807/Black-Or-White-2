package inputsMovementDemo;

import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.ioc.ReleaseIocFactory;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.GameApplication;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;

import static com.pokewords.framework.sprites.factories.SpriteInitializer.InitializationMode.LAZY;

public class InputsMovementDemo extends GameApplication {

    public InputsMovementDemo(IocFactory iocFactory) {
        super(iocFactory);
    }

    @Override
    protected void onGameWindowsConfiguration(GameWindowsConfigurator gameWindowsConfigurator) {
        gameWindowsConfigurator.name("Basic App Demo")
                            .atCenter();
    }

    @Override
    protected void onSpriteDeclaration(SpriteInitializer spriteInitializer) {
        spriteInitializer.setInitializationMode(LAZY);

    }

    @Override
    protected void onAppStatesConfiguration(AppStateMachine asm) {
        MainAppState mainAppState = asm.createState(MainAppState.class);
        asm.setGameInitialState(mainAppState);
    }


    public static void main(String[] args) {
        InputsMovementDemo app = new InputsMovementDemo(new ReleaseIocFactory());
        app.launch();
    }
}
