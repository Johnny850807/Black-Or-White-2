package com.pokewords;

import com.pokewords.appstates.MenuAppState;
import com.pokewords.constants.Events;
import com.pokewords.constants.SoundTypes;
import com.pokewords.constants.SpriteTypes;
import com.pokewords.framework.commons.Range;
import com.pokewords.framework.commons.utils.GifScriptMaker;
import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.engine.asm.states.multiplayer.MultiplayerRoomState;
import com.pokewords.framework.engine.weaver.Set0FrameAsCurrentNodeWeaverNode;
import com.pokewords.framework.ioc.IocContainer;
import com.pokewords.framework.ioc.ReleaseIocContainer;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.GameApplication;
import com.pokewords.framework.views.SoundPlayer;
import com.pokewords.framework.views.effects.AppStateTransitionEffect;
import com.pokewords.framework.views.effects.CrossFadingTransitionEffect;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;

public class BlackOrWhite extends GameApplication {

    public BlackOrWhite(IocContainer iocContainer) {
        super(iocContainer);
    }

    @Override
    protected void onGameWindowsConfiguration(GameWindowsConfigurator gameWindowsConfigurator) {
        gameWindowsConfigurator.name("Black Or White")
                        .atCenter();
    }

    @Override
    protected void onSoundPlayerConfiguration(SoundPlayer soundPlayer) {
        soundPlayer.addSound(SoundTypes.OPENING, SoundTypes.OPENING.getSoundPath());
    }

    @Override
    protected void onSpriteDeclaration(SpriteInitializer spriteInitializer) {
        spriteInitializer.declare(SpriteTypes.MENU)
                .area(0, 0, 800, 600)
                .with(GifScriptMaker.createSequenceScript("assets/sequences/menu",
                        new Range(0, 57), 0, 57, 30, 58))
                .weaver(new Set0FrameAsCurrentNodeWeaverNode())
                .commit();
    }

    @Override
    protected void onAppStatesConfiguration(AppStateMachine asm) {
        MenuAppState menuAppState = asm.createState(MenuAppState.class);
        asm.setGameInitialState(menuAppState, new AppStateTransitionEffect.DefaultListener() {
            @Override
            public void onEnteringAppStateEffectEnd() {
                getSoundPlayer().playSound(SoundTypes.OPENING);
            }
        });

        MultiplayerRoomState multiplayerRoomState = asm.createState(MultiplayerRoomState.class);
        asm.addTransition(menuAppState, Events.TO_MULTIPLAYER, multiplayerRoomState, new CrossFadingTransitionEffect());
    }


    public static void main(String[] args) {
        new BlackOrWhite(new ReleaseIocContainer()).launch();
    }
}
