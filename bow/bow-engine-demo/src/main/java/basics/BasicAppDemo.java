package basics;

import basics.components.BulletComponent;
import basics.components.CharacterComponent;
import basics.components.MonsterAI;
import basics.namespace.AppStateEvent;
import basics.namespace.ComponentName;
import basics.namespace.SpriteType;
import basics.states.GameOverAppState;
import basics.states.MainAppState;
import basics.weaver.MyWeaverNode;
import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.ioc.ReleaseIocFactory;
import com.pokewords.framework.sprites.SpriteInitializer;
import com.pokewords.framework.views.GameApplication;
import com.pokewords.framework.views.GameWindowsConfigurator;

import java.awt.*;

public class BasicAppDemo extends GameApplication {

    public BasicAppDemo(IocFactory iocFactory) {
        super(iocFactory);
    }

    @Override
    protected void onGameWindowsConfiguration(GameWindowsConfigurator gameWindowsConfigurator) {
        gameWindowsConfigurator.name("Basic BasicAppDemo Demo")
                            .size(600, 600)
                            .resizable(false)
                            .backgroundColor(Color.black)
                            .atCenter();
    }

    @Override
    protected void onSpriteInitializer(SpriteInitializer spriteInitializer) {
        spriteInitializer.declare(SpriteType.HERO)
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
                .commit();
    }

    @Override
    protected void onAppStatesConfiguration(AppStateMachine asm) {
        MainAppState mainAppState = new MainAppState(asm);
        GameOverAppState gameOverAppState = new GameOverAppState(asm);
        asm.addState(mainAppState);
        asm.addState(gameOverAppState);
        asm.setGameInitialState(mainAppState);
        asm.addTransition(mainAppState, AppStateEvent.OVER, gameOverAppState);
    }


    public static void main(String[] args) {
        BasicAppDemo app = new BasicAppDemo(new ReleaseIocFactory());
        app.launch();
    }
}
