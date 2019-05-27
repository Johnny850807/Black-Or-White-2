package com.pokewords.framework.engine.asm.states;

import com.pokewords.framework.commons.Range;
import com.pokewords.framework.commons.utils.GifFrameStateMachineComponentMaker;
import com.pokewords.framework.commons.utils.GifScriptMaker;
import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.engine.weaver.Set0FrameAsCurrentNodeWeaverNode;
import com.pokewords.framework.sprites.components.StringComponent;
import com.pokewords.framework.sprites.components.frames.StringFrame;

import java.awt.*;

/**
 * A cool animated loading state scene with a breaker at the center and water drop sound effect.
 * The background color is #191F26 which looks really great.
 * @author johnny850807 (waterball)
 */
public class BreakerIconLoadingState extends AppState {

    private enum Types {
        BreakerIconLoadingState, AnimationSourceText, WaterDropSoundEffect
    }

    @Override
    protected boolean isListeningToInputEvents() {
        return false;
    }

    @Override
    protected void onAppStateCreating(AppStateWorld appStateWorld) {
        getGameWindowsConfigurator().gameSize(800, 600);

        declareAndSpawnBreakerIconLoadingAnimation();
        declareAndSpawnAnimationSourceText();

        getSoundPlayer().addSound(Types.WaterDropSoundEffect, "assets/sounds/WaterDropSound.wav");
    }

    private void declareAndSpawnBreakerIconLoadingAnimation() {
        getAppStateWorld().spawn(
                getSpriteInitializer().declare(Types.BreakerIconLoadingState)
                        .area(0, 0, getGameWindowDefinition().size)
                        .with(GifFrameStateMachineComponentMaker.fromSequenceGallery( "assets/sequences/BreakerLoadingIcon",
                                new Range(0, 249), 0, 249, 30, 0))
                        .weaver(new Set0FrameAsCurrentNodeWeaverNode())
                        .commit()
                        .create()
        );
    }

    private void declareAndSpawnAnimationSourceText() {
        getAppStateWorld().spawn(
                getSpriteInitializer().declare(Types.AnimationSourceText)
                        .position(165, 575)
                        .with(new StringComponent(
                                new StringFrame(1,
                                "Animation Source: https://www.reddit.com/r/loadingicon/comments/7cwyib/beaker_loading_icon/")
                                    .color(Color.white)
                                    .font(new Font("@Microsoft JhengHei UI", Font.PLAIN, 15))))
                        .commit()
                        .create()
        );
    }

    @Override
    protected void onAppStateEntering() {
        getSoundPlayer().playSoundLoopingForever(Types.WaterDropSoundEffect);
    }

    @Override
    protected void onAppStateExiting() {
        getSoundPlayer().stopSound(Types.WaterDropSoundEffect);
    }

    @Override
    protected void onAppStateDestroying() { }

    @Override
    protected void onAppStateUpdating(double timePerFrame) { }

}
