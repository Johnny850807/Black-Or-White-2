package inputsMovementDemo;

import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.ioc.ReleaseIocFactory;
import com.pokewords.framework.sprites.components.ImageComponent;
import com.pokewords.framework.sprites.components.frames.ImageFrame;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.GameApplication;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;

public class InputsMovementDemo extends GameApplication {

    public InputsMovementDemo(IocFactory iocFactory) {
        super(iocFactory);
    }

    @Override
    protected void onGameWindowsConfiguration(GameWindowsConfigurator gameWindowsConfigurator) {
        gameWindowsConfigurator.name("Inputs Movement Demo")
                            .atCenter();
    }

    @Override
    protected void onSpriteDeclaration(SpriteInitializer spriteInitializer) {
        spriteInitializer.declare(MainAppState.Types.SMILE)
                .with(new ImageComponent(new ImageFrame(0, 2, 50, 50, "images/smile.png", true)))
                .position(getGameWindowDefinition().center())
                .commit();
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
