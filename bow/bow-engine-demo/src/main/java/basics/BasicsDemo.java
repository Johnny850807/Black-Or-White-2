package basics;

import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.ioc.ReleaseIocFactory;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.GameApplication;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;
import inputsMovementDemo.MainAppState;

import static com.pokewords.framework.sprites.factories.SpriteInitializer.InitializationMode.LAZY;

public class BasicsDemo extends GameApplication {

    public BasicsDemo(IocFactory iocFactory) {
        super(iocFactory);
    }

    @Override
    protected void onGameWindowsConfiguration(GameWindowsConfigurator gameWindowsConfigurator) {
        gameWindowsConfigurator.name("Basic App Demo")
                            .atCenter();
    }

    @Override
    protected void onSpriteDeclaration(SpriteInitializer spriteInitializer) {
        spriteInitializer.declare(basics.MainAppState.Sprites.CHARACTER)
                .position(getGameWindowDefinition().center())
                .with("scripts/character.bow")
                .weaver(new MovementTransitionsWeaverNode())
                .commit();
    }

    @Override
    protected void onAppStatesConfiguration(AppStateMachine asm) {
        MainAppState mainAppState = asm.createState(MainAppState.class);
        asm.setGameInitialState(mainAppState);
    }


    public static void main(String[] args) {
        BasicsDemo app = new BasicsDemo(new ReleaseIocFactory());
        app.launch();
    }
}