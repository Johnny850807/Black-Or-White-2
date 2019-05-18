package com.pokewords.framework.engine.asm;

import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.engine.weaver.Set0FrameAsCurrentNodeWeaverNode;
import com.pokewords.framework.sprites.components.StringComponent;
import com.pokewords.framework.sprites.components.frames.StringFrame;
import com.pokewords.framework.sprites.parsing.*;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

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
    protected void onAppStateCreating(AppStateWorld appStateWorld) {
        getGameWindowsConfigurator().size(800, 600);

        declareAndSpawnBreakerIconLoadingAnimation();
        declareAndSpawnAnimationSourceText();

        getSoundPlayer().addSound(Types.WaterDropSoundEffect, "assets/sounds/WaterDropSound.wav");
    }

    private void declareAndSpawnBreakerIconLoadingAnimation() {
        getAppStateWorld().spawn(
                getSpriteInitializer().declare(Types.BreakerIconLoadingState)
                        .position(0, 0)
                        .size(getGameWindowDefinition().size)
                        .with(createScript())
                        .weaver(new Set0FrameAsCurrentNodeWeaverNode())
                        .commit()
                        .create()
        );
    }

    private void declareAndSpawnAnimationSourceText() {
        getAppStateWorld().spawn(
                getSpriteInitializer().declare(Types.AnimationSourceText)
                        .position(155, 560)
                        .with(new StringComponent(new StringFrame(0, 1,
                                "Animation Source: https://www.reddit.com/r/loadingicon/comments/7cwyib/beaker_loading_icon/",
                                Color.white, new Font("@Microsoft JhengHei UI", Font.PLAIN, 15), false)))
                        .commit()
                        .create()

        );
    }

    @SuppressWarnings("Duplicates")
    private Script createScript() {
        Script script = new LinScript();
        Segment galleriesSegment = new LinScriptSegment("galleries", 0);
        Element sequenceElement = new LinScriptElement("sequence");
        sequenceElement.put("startPic", 0);
        sequenceElement.put("endPic", 249);
        sequenceElement.put("path", "assets/sequences/BeakerLoadingIcon");
        script.addSegment(galleriesSegment);
        galleriesSegment.addElement(sequenceElement);

        for (int i = 0; i <= 249; i++) {
            LinScriptSegment frameSegment = new LinScriptSegment("frame", i);
            frameSegment.put("pic", i);
            frameSegment.put("duration", 30);
            frameSegment.put("next", (i + 1) % 250);
            frameSegment.put("layer", 0);
            script.addSegment(frameSegment);
        }
        return script;
    }

    @Override
    protected void onAppStateEntering() {
        getSoundPlayer().playSoundLoopingForever(Types.WaterDropSoundEffect);
    }

    @Override
    protected void onAppStateExiting() {
        getSoundPlayer().stop(Types.WaterDropSoundEffect);
    }

    @Override
    protected void onAppStateDestroying() {
    }

    @Override
    protected void onAppStateUpdating(double timePerFrame) {
    }


    public static void main(String[] args) {
        Script script = new LinScript();
        Segment galleriesSegment = new LinScriptSegment("galleries", 0);
        Element sequenceElement = new LinScriptElement("sequence");
        sequenceElement.put("startPic", 0);
        sequenceElement.put("endPic", 249);
        sequenceElement.put("path", "assets/sequences/BeakerLoadingIcon");
        script.addSegment(galleriesSegment);
        galleriesSegment.addElement(sequenceElement);

        for (int i = 0; i <= 249; i++) {
            LinScriptSegment frameSegment = new LinScriptSegment("frame", i);
            frameSegment.put("pic", i);
            frameSegment.put("duration", 30);
            frameSegment.put("next", (i + 1) % 250);
            frameSegment.put("layer", 0);
            script.addSegment(frameSegment);
        }

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection( script.toString(4) );
        clipboard.setContents(stringSelection, stringSelection);
    }
}
