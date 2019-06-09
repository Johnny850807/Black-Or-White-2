package basics;

import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.ioc.IocContainer;
import com.pokewords.framework.ioc.ReleaseIocContainer;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.RigidBodyComponent;
import com.pokewords.framework.sprites.components.frames.EffectFrame;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.sprites.factories.SpriteWeaver;
import com.pokewords.framework.sprites.parsing.Script;
import com.pokewords.framework.views.GameApplication;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;

public class BasicsDemo extends GameApplication {

    public BasicsDemo(IocContainer iocContainer) {
        super(iocContainer);
    }

    @Override
    protected void onGameWindowsConfiguration(GameWindowsConfigurator gameWindowsConfigurator) {
        gameWindowsConfigurator
                .name("Basic App Demo")
                .atCenter();
    }

    @Override
    protected void onSpriteDeclaration(SpriteInitializer spriteInitializer) {
        spriteInitializer
                .declare(basics.MainAppState.Sprites.CHARACTER)
                .position(getGameWindowDefinition().center())
                .with("scripts/character.bow")
                .with(RigidBodyComponent.getInstance())
                .with(new PlayerKeyListenerComponent())
                .areaSize(67, 77)
                .weaver(new SpriteWeaver.Node() {
                    @Override
                    public void onWeaving(Script script, Sprite sprite, IocContainer iocContainer) {
                        EffectFrame frame = sprite.getFrameStateMachineComponent().getFrame(999);
                        sprite.getFrameStateMachineComponent().setCurrentFrame(frame);
                    }
                })
                .commit();
    }

    @Override
    protected void onAppStatesConfiguration(AppStateMachine asm) {
        asm.setGameInitialState(asm.createState(MainAppState.class));
    }


    public static void main(String[] args) {
        BasicsDemo app = new BasicsDemo(new ReleaseIocContainer());
        app.launch();
    }
}
