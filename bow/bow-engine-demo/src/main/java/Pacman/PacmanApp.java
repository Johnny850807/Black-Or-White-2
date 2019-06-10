package Pacman;

import com.pokewords.framework.commons.utils.GifFrameStateMachineComponentMaker;
import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.engine.weaver.Set0FrameAsCurrentNodeWeaverNode;
import com.pokewords.framework.ioc.IocContainer;
import com.pokewords.framework.ioc.ReleaseIocContainer;
import com.pokewords.framework.sprites.components.*;
import com.pokewords.framework.sprites.components.frames.ImageFrameFactory;
import com.pokewords.framework.sprites.components.frames.StringFrame;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.GameApplication;
import com.pokewords.framework.views.SoundPlayer;
import com.pokewords.framework.views.effects.AppStateTransitionEffect;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;

import java.awt.*;

public class PacmanApp extends GameApplication {

    public PacmanApp(IocContainer iocContainer) {
        super(iocContainer);
    }

    @Override
    protected void onGameWindowsConfiguration(GameWindowsConfigurator gameWindowsConfigurator) {
        gameWindowsConfigurator.name("Homework")
                .gamePanelBackground(Color.decode("#404040"))
                .atCenter();
    }

    @Override
    protected void onSpriteDeclaration(SpriteInitializer spriteInitializer) {
        getSoundPlayer().addSound(Types.PACE, "sounds/pace.wav");
        getSoundPlayer().addSound(Types.SHOOT, "sounds/machineGun.wav");

        spriteInitializer.declare(Types.AI_PARENT)
                .with(new AiComponent())
                .with(new MonsterCollisionListenerComponent())
                .with(RigidBodyComponent.getInstance())
                .weaver(new Set0FrameAsCurrentNodeWeaverNode())
                .areaSize(35, 35)
                .body(2, 2, 33, 33)
                .commit();

        spriteInitializer.declareFromParent(Types.AI_PARENT, Types.AI1)
                .with(GifFrameStateMachineComponentMaker.fromSheet("sheets/pacManSheet.png",
                        8, 8, 22, 23, 8, 1))
                .with(new MovingComponent(5))
                .with(new GunComponent(20, 8))
                .with(new HpComponent(5))
                .commit();

        spriteInitializer.declareFromParent(Types.AI_PARENT, Types.AI2)
                .with(GifFrameStateMachineComponentMaker.fromSheet("sheets/pacManSheet.png",
                        8, 8, 30, 31, 8, 1))
                .with(new MovingComponent(8))
                .with(new GunComponent(10, 14))
                .with(new HpComponent(8))
                .commit();

        spriteInitializer.declareFromParent(Types.AI_PARENT, Types.AI3)
                .with(GifFrameStateMachineComponentMaker.fromSheet("sheets/pacManSheet.png",
                        8, 8, 38, 39, 8, 1))
                .with(new MovingComponent(12))
                .with(new GunComponent(3, 22))
                .with(new HpComponent(10))
                .commit();

        spriteInitializer.declare(Types.PLAYER)
                .with(new ImageComponent(ImageFrameFactory.fromPath(1, "images/smile.png")))
                .with(new PlayerKeyListenerComponent())
                .with(new PacmanCollisionListenerComponent())
                .with(new GunComponent(7, 20))
                .with(new MovingComponent(12))
                .with(new HpComponent(8))
                .with(RigidBodyComponent.getInstance())
                .with(new PlayerMouseListenerComponent())
                .areaSize( 50, 50)
                .body(1, 1, 49, 49)
                .commit();

        spriteInitializer.declare(Types.MOUSE_POSITION)
                .with(new StringComponent(
                        new StringFrame(5, "")
                                .font(new Font("微軟正黑體", Font.PLAIN, 25))
                                .color(Color.black)
                                .flags(StringFrame.FLAG_STICK_SPRITE_AREA)))
                .with(new TextMouseListenerComponent())
                .position(0, 0)
                .commit();

        spriteInitializer.declare(Types.BULLET)
                .areaSize(5, 5)
                .with(new BulletComponent())
                .with(new RemoveSelfIfOutOfScreen())
                .with(new BulletCollisionListenerComponent())
                .commit();
    }

    @Override
    protected void onSoundPlayerConfiguration(SoundPlayer soundPlayer) {
        soundPlayer.addSound(Types.DODO, "sounds/dodo.wav");
    }

    @Override
    protected void onAppStatesConfiguration(AppStateMachine asm) {
        MainAppState mainAppState = asm.createState(MainAppState.class);
        asm.setGameInitialState(mainAppState, new AppStateTransitionEffect.DefaultListener() {
            @Override
            public void onExitingAppStateEffectEnd() {
                getSoundPlayer().playSound(Types.DODO);
            }
        });
    }

    public static void main(String[] args) {
        new PacmanApp(new ReleaseIocContainer()).launch();
    }
}
