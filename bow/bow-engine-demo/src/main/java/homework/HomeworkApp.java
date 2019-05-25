package homework;

import com.pokewords.framework.commons.utils.GifScriptMaker;
import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.engine.weaver.Set0FrameAsCurrentNodeWeaverNode;
import com.pokewords.framework.ioc.IocContainer;
import com.pokewords.framework.ioc.ReleaseIocContainer;
import com.pokewords.framework.sprites.components.ImageComponent;
import com.pokewords.framework.sprites.components.KeyListenerComponent;
import com.pokewords.framework.sprites.components.MouseListenerComponent;
import com.pokewords.framework.sprites.components.StringComponent;
import com.pokewords.framework.sprites.components.frames.ImageFrameFactory;
import com.pokewords.framework.sprites.components.frames.StringFrame;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.GameApplication;
import com.pokewords.framework.views.SoundPlayer;
import com.pokewords.framework.views.effects.AppStateTransitionEffect;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;

import java.awt.*;

public class HomeworkApp extends GameApplication {

    public HomeworkApp(IocContainer iocContainer) {
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
        spriteInitializer.declare(Types.AI1)
                .with(GifScriptMaker.createSheetScript("sheets/pacManSheet.png",
                        8, 8, 22, 23, 120, 1 ))
                .with(new AiComponent())
                .weaver(new Set0FrameAsCurrentNodeWeaverNode())
                .area(597, 130, 35, 35)
                .commit();

        spriteInitializer.declare(Types.AI2)
                .with(GifScriptMaker.createSheetScript("sheets/pacManSheet.png",
                        8, 8, 30, 31, 120, 1 ))
                .with(new AiComponent())
                .weaver(new Set0FrameAsCurrentNodeWeaverNode())
                .area(339, 145, 35, 35)
                .commit();

        spriteInitializer.declare(Types.AI3)
                .with(GifScriptMaker.createSheetScript("sheets/pacManSheet.png",
                        8, 8, 38, 39, 120, 1 ))
                .with(new AiComponent())
                .weaver(new Set0FrameAsCurrentNodeWeaverNode())
                .area(98, 127, 35, 35)
                .commit();

        spriteInitializer.declare(Types.PLAYER)
                .with(new ImageComponent(ImageFrameFactory.fromPath(1, "images/smile.png")))
                .with(new KeyListenerComponent(new PlayerKeyListener()))
                .with(MouseListenerComponent.ofListener(new PlayerMouseListener()))
                .area(346, 453, 50, 50)
                .commit();

        spriteInitializer.declare(Types.MOUSE_POSITION)
                .with(new StringComponent(
                        new StringFrame(5, "")
                            .font(new Font("微軟正黑體", Font.PLAIN, 25))
                                .color(Color.black)
                            .flags(StringFrame.FLAG_STICK_SPRITE_AREA)))
                .with(MouseListenerComponent.ofListener(new TextMouseListener()))
                .position(0, 0)
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
        new HomeworkApp(new ReleaseIocContainer()).launch();
    }
}
