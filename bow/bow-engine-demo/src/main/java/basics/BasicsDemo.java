package basics;

import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.ioc.ReleaseIocFactory;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.GameApplication;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;

import static com.pokewords.framework.sprites.factories.SpriteInitializer.InitializationMode.LAZY;
import static com.pokewords.framework.sprites.factories.SpriteInitializer.InitializationMode.NON_LAZY;

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
        spriteInitializer.setInitializationMode(NON_LAZY);
        spriteInitializer.declare(basics.MainAppState.Sprites.CHARACTER)
                .position(getGameWindowDefinition().center())
                .with("scripts/character.bow")
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
