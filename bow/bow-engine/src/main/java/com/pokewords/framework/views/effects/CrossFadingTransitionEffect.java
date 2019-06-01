package com.pokewords.framework.views.effects;

import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;
import com.pokewords.framework.sprites.components.frames.EffectFrame;
import com.pokewords.framework.sprites.components.frames.GameEffect;
import com.pokewords.framework.sprites.components.frames.RectangleFrame;
import com.pokewords.framework.sprites.factories.SpriteBuilder;
import com.pokewords.framework.views.SoundPlayer;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;

import java.awt.*;

/**
 * A cross fading transition effect, the most normal transition effect.
 *
 * @author johnny850807 (waterball)
 */
public class CrossFadingTransitionEffect implements AppStateTransitionEffect {
    public final static int FADED_IN_RECTANGLE_ID = 9990;
    public final static int FADED_OUT_RECTANGLE_ID = 9991;
    private SpriteBuilder spriteBuilder;
    private AppState from;
    private AppState to;
    private Listener[] transitionEffectListeners;
    private SoundPlayer soundPlayer;
    private Dimension gameWindowsSize;

    private enum Types {
        FADED_IN_RECTANGLE, FADED_OUT_RECTANGLE
    }

    private Object soundType;

    public CrossFadingTransitionEffect() {
    }

    public CrossFadingTransitionEffect(Object soundType) {
        this.soundType = soundType;
    }

    @Override
    public synchronized void effect(SpriteBuilder spriteBuilder, AppState from, AppState to, Listener... transitionEffectListeners) {
        this.spriteBuilder = spriteBuilder;
        this.from = from;
        this.to = to;
        this.transitionEffectListeners = transitionEffectListeners;
        this.soundPlayer = from.getSoundPlayer();
        this.gameWindowsSize = from.getWindowSize();

        declareEffectSpritesAndSpawn();
    }


    private void declareEffectSpritesAndSpawn() {
        Sprite fadedInRectangle = createFadedInRectangle();
        Sprite fadedOutRectangle = createFadedOutRectangle();

        from.getAppStateWorld().spawn(fadedInRectangle);
        to.getAppStateWorld().spawn(fadedOutRectangle);
    }


    private Sprite createFadedInRectangle() {
        Color transparentWhite = new Color(255, 255, 255, 0);
        EffectFrame rectangleEffectFrame = createFadingEffectRectangleFrame(transparentWhite, true);

        PropertiesComponent propertiesComponent = new PropertiesComponent(Types.FADED_IN_RECTANGLE);
        propertiesComponent.setArea(0, 0, gameWindowsSize.width, gameWindowsSize.height);

        return spriteBuilder.init().setPropertiesComponent(propertiesComponent)
                .setFSMComponent(createFrameStateMachineComponentWithFadingEffect(rectangleEffectFrame))
                .build();
    }


    private class FadedInEffect implements GameEffect {
        private RectangleFrame rectangleFrame;
        private EffectFrame rectangleEffectFrame;
        private Color latestColor;

        public FadedInEffect(RectangleFrame rectangleFrame, EffectFrame rectangleEffectFrame) {
            this.rectangleFrame = rectangleFrame;
            this.rectangleEffectFrame = rectangleEffectFrame;
        }

        @Override
        public void onFirstApply(AppStateWorld world, Sprite sprite) {
            if (soundType != null)
                soundPlayer.playSound(soundType);
        }

        @Override
        public void apply(AppStateWorld appStateWorld, Sprite sprite) {
            this.latestColor = rectangleFrame.getColor();
            fadeInTheColor();
            endUpEffectIfAlphaEqual255();
        }

        private void fadeInTheColor() {
            int alpha = latestColor.getAlpha() + 5 > 255 ? 255 : latestColor.getAlpha() + 5;
            rectangleFrame.setColor(new Color(255, 255, 255, alpha));
        }

        private void endUpEffectIfAlphaEqual255() {
            if (latestColor.getAlpha() == 255) {
                from.getAppStateWorld().removeSprite(rectangleEffectFrame.getSprite());
                notifyOnExitingAppStateEffectEnd(transitionEffectListeners);
            }
        }
    }


    private Sprite createFadedOutRectangle() {
        EffectFrame rectangleEffectFrame = createFadingEffectRectangleFrame(Color.white, false);

        PropertiesComponent propertiesComponent = new PropertiesComponent(Types.FADED_OUT_RECTANGLE);
        propertiesComponent.setArea(0, 0, gameWindowsSize.width, gameWindowsSize.height);

        return spriteBuilder.init().setPropertiesComponent(propertiesComponent)
                .setFSMComponent(createFrameStateMachineComponentWithFadingEffect(rectangleEffectFrame))
                .build();
    }

    private EffectFrame createFadingEffectRectangleFrame(Color startedColor, boolean fadeIn) {
        int id = fadeIn ? FADED_IN_RECTANGLE_ID : FADED_OUT_RECTANGLE_ID;

        RectangleFrame rectangleFrame = new RectangleFrame(Integer.MAX_VALUE, startedColor)
                .flags(RectangleFrame.CANVAS_FLAG_FILLED);

        EffectFrame rectangleEffectFrame = EffectFrame.wrap(rectangleFrame, 999, 15);

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
        public void onFirstApply(AppStateWorld world, Sprite sprite) {

        }

        @Override
        public void apply(AppStateWorld appStateWorld, Sprite sprite) {
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
            if (latestColor.getAlpha() == 0) {
                to.getAppStateWorld().removeSprite(rectangleEffectFrame.getSprite());
                notifyOnEnteringAppStateEffectEnd(transitionEffectListeners);
            }
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
