package basics;

import basics.namespace.AppStateEvent;
import basics.states.GameOverAppState;
import basics.states.MainAppState;
import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.ioc.ReleaseIocFactory;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.GameApplication;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;

import java.awt.*;

import static com.pokewords.framework.sprites.factories.SpriteInitializer.InitializationMode.LAZY;

public class BasicAppDemo extends GameApplication {

    public BasicAppDemo(IocFactory iocFactory) {
        super(iocFactory);
    }

    @Override
    protected void onGameWindowsConfiguration(GameWindowsConfigurator gameWindowsConfigurator) {
        gameWindowsConfigurator.name("BasicAppDemo Demo")
                            .size(600, 600)
                            .gamePanelBackground(Color.decode("#1B4032"))
                            .atCenter();
    }

    @Override
    protected void onSpriteInitializer(SpriteInitializer spriteInitializer) {
        spriteInitializer.setInitializationMode(LAZY);

        /*spriteInitializer.declare(SpriteType.HERO)
                    .with("path/to/hero.bow")
                    .position(280, 500)
                    .with(ComponentName.CHARACTER, new CharacterComponent())
                    .collidable()
                    .weaver(new MyWeaverNode())
                    .commit();

        spriteInitializer.declare(SpriteType.MONSTER)
                    .with("path/to/monster.bow")
                    .with(ComponentName.CHARACTER, new CharacterComponent())
                    .with(ComponentName.MONSTER_AI, new MonsterAI())
                    .collidable()                    .weaver(new MyWeaverNode())
                    .commit();

        spriteInitializer.declare(SpriteType.BULLET)
                .with("path/to/bullet.bow")
                .with(ComponentName.BULLET, new BulletComponent())
                .collidable()
                .weaver(new MyWeaverNode())
                .commit();*/
    }

    @Override
    protected void onAppStatesConfiguration(AppStateMachine asm) {
        MainAppState mainAppState = asm.createState(MainAppState.class);
        GameOverAppState gameOverAppState = asm.createState(GameOverAppState.class);
        asm.setGameInitialState(mainAppState);
        asm.addTransition(mainAppState, AppStateEvent.OVER, gameOverAppState);
    }


    public static void main(String[] args) {
        BasicAppDemo app = new BasicAppDemo(new ReleaseIocFactory());
        app.launch();
    }
}
