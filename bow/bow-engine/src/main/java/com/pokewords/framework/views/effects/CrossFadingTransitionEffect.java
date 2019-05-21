package com.pokewords.framework.views.effects;

import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.frames.EffectFrame;
import com.pokewords.framework.sprites.components.frames.GameEffect;
import com.pokewords.framework.sprites.components.frames.RectangleFrame;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.SoundPlayer;

import java.awt.*;

/**
 * A cross fading transition effect, the most normal transition effect.
 * @author johnny850807 (waterball)
 */
public class CrossFadingTransitionEffect implements AppStateTransitionEffect {
    private AppState from;
    private AppState to;
    private Listener[] transitionEffectListeners;
    private SoundPlayer soundPlayer;
    private SpriteInitializer spriteInitializer;

    private enum Types {
        FADED_IN_RECTANGLE, FADED_OUT_RECTANGLE
    }

    private Object soundType;

    public CrossFadingTransitionEffect() { }

    public CrossFadingTransitionEffect(Object soundType) {
        this.soundType = soundType;
    }

    @Override
    public synchronized void effect(AppState from, AppState to, Listener... transitionEffectListeners) {
        this.from = from;
        this.to = to;
        this.transitionEffectListeners = transitionEffectListeners;
        this.soundPlayer = from.getSoundPlayer();
        this.spriteInitializer = from.getSpriteInitializer();

        declareEffectSpritesAndSpawn();
    }


    private void declareEffectSpritesAndSpawn() {
        if (!spriteInitializer.hasDeclared(Types.FADED_IN_RECTANGLE))
            declareFadedInRectangle();
        if (!spriteInitializer.hasDeclared(Types.FADED_OUT_RECTANGLE))
            declareFadedOutRectangle();

        from.getAppStateWorld().spawn(spriteInitializer.createSprite(Types.FADED_IN_RECTANGLE));
        to.getAppStateWorld().spawn(spriteInitializer.createSprite(Types.FADED_OUT_RECTANGLE));
    }


    private void declareFadedInRectangle() {
        Color transparentWhite = new Color(255, 255, 255, 0);
        EffectFrame rectangleEffectFrame = createFadingEffectRectangleFrame(transparentWhite, true);

        spriteInitializer.declare(Types.FADED_IN_RECTANGLE)
                .position(0, 0)
                .areaSize(from.getGameWindowDefinition().size)
                .with(createFrameStateMachineComponentWithFadingEffect(rectangleEffectFrame))
                .commit();
    }


    private class FadedInEffect implements GameEffect {
        private RectangleFrame rectangleFrame;
        private EffectFrame rectangleEffectFrame;
        private boolean justStarted = true;
        private Color latestColor;

        public FadedInEffect(RectangleFrame rectangleFrame, EffectFrame rectangleEffectFrame) {
            this.rectangleFrame = rectangleFrame;
            this.rectangleEffectFrame = rectangleEffectFrame;
        }

        @Override
        public void accept(AppStateWorld appStateWorld, Sprite sprite) {
            this.latestColor = rectangleFrame.getColor();
            playSoundIfJustStarted();
            fadeInTheColor();
            endUpEffectIfAlphaEqual255();
        }

        private void playSoundIfJustStarted() {
            if (justStarted) {
                justStarted = false;
                soundPlayer.playSound(soundType);
            }
        }

        private void fadeInTheColor() {
            int alpha = latestColor.getAlpha() + 5 > 255 ? 255 : latestColor.getAlpha() + 5;
            rectangleFrame.setColor(new Color(255, 255, 255, alpha));
        }

        private void endUpEffectIfAlphaEqual255() {
            if (latestColor.getAlpha() == 255)
            {
                from.getAppStateWorld().removeSprite(rectangleEffectFrame.getSprite());
                notifyOnExitingAppStateEffectEnd(transitionEffectListeners);
            }
        }
    }


    private void declareFadedOutRectangle() {
        EffectFrame rectangleEffectFrame = createFadingEffectRectangleFrame(Color.white, false);
        spriteInitializer.declare(Types.FADED_OUT_RECTANGLE)
                .position(0, 0)
                .areaSize(to.getGameWindowDefinition().size)
                .with(createFrameStateMachineComponentWithFadingEffect(rectangleEffectFrame))
                .commit();

    }

    private EffectFrame createFadingEffectRectangleFrame(Color startedColor, boolean fadeIn) {
        RectangleFrame rectangleFrame = new RectangleFrame(0, Integer.MAX_VALUE, startedColor);
        EffectFrame rectangleEffectFrame = EffectFrame.wrap(rectangleFrame, 15);

        GameEffect fadingEffect = fadeIn ? new FadedInEffect(rectangleFrame, rectangleEffectFrame)
                : new FadeOutEffect(rectangleFrame, rectangleEffectFrame);

        rectangleEffectFrame.addEffect(fadingEffect);
        return rectangleEffectFrame;
    }

    private FrameStateMachineComponent createFrameStateMachineComponentWithFadingEffect(EffectFrame fadingEffectFrame) {
        FrameStateMachineComponent frameStateMachineComponent = new FrameStateMachineComponent();
        frameStateMachineComponent.addFrame(fadingEffectFrame);
        frameStateMachineComponent.setCurrentFrame(fadingEffectFrame);
        return frameStateMachineComponent;
    }

    private class FadeOutEffect implements GameEffect {
        private RectangleFrame rectangleFrame;
        private EffectFrame rectangleEffectFrame;
        private Color latestColor;

        public FadeOutEffect(RectangleFrame rectangleFrame, EffectFrame rectangleEffectFrame) {
            this.rectangleFrame = rectangleFrame;
            this.rectangleEffectFrame = rectangleEffectFrame;
        }

        @Override
        public void accept(AppStateWorld appStateWorld, Sprite sprite) {
            this.latestColor = rectangleFrame.getColor();

            fadeOutTheColor();
            endUpEffectIfAlphaEqual0();
        }

        private void fadeOutTheColor() {
            int alpha = latestColor.getAlpha() - 8 < 0 ? 0 : latestColor.getAlpha() - 8;
            rectangleFrame.setColor(new Color(latestColor.getBlue(), latestColor.getGreen(),
                    latestColor.getBlue(), alpha));
        }

        private void endUpEffectIfAlphaEqual0() {
            if (latestColor.getAlpha() == 0)
            {
                to.getAppStateWorld().removeSprite(rectangleEffectFrame.getSprite());
                notifyOnEnteringAppStateEffectEnd(transitionEffectListeners);
            }
        }
    }
}
