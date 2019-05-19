package com.pokewords;

import com.pokewords.appstates.MenuAppState;
import com.pokewords.constants.Events;
import com.pokewords.constants.SoundTypes;
import com.pokewords.constants.SpriteTypes;
import com.pokewords.framework.commons.utils.GifScriptMaker;
import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.engine.weaver.Set0FrameAsCurrentNodeWeaverNode;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.ioc.ReleaseIocFactory;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.GameApplication;
import com.pokewords.framework.views.SoundPlayer;
import com.pokewords.framework.views.effects.AppStateTransitionEffect;
import com.pokewords.framework.views.effects.NoTransitionEffect;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;

public class BlackOrWhite extends GameApplication {

    public BlackOrWhite(IocFactory iocFactory) {
        super(iocFactory);
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
                .position(0, 0)
                .size(800, 600)
                .with(GifScriptMaker.createScript("sequence", "assets/sequences/menu",
                        30, 58, 0))
                .weaver(new Set0FrameAsCurrentNodeWeaverNode())
                .commit();
    }

    @Override
    protected void onAppStatesConfiguration(AppStateMachine asm) {
        MenuAppState menuAppState = asm.createState(MenuAppState.class);
        asm.setGameInitialState(menuAppState, new AppStateTransitionEffect.DefaultListener() {
            @Override
            public void onToEffectEnd() {
                getSoundPlayer().playSound(SoundTypes.OPENING);
            }
        });
    }


    public static void main(String[] args) {
        new BlackOrWhite(new ReleaseIocFactory()).launch();
    }
}
