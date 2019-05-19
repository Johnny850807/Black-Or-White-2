package com.pokewords.framework.views.effects;

import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.FrameComponent;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.frames.EffectFrame;
import com.pokewords.framework.sprites.components.frames.GameEffect;
import com.pokewords.framework.sprites.components.frames.RectangleFrame;
import com.pokewords.framework.sprites.factories.SpriteInitializer;

import java.awt.*;

/**
 * A cross fading transition effect, the most normal transition effect.
 * @author johnny850807 (waterball)
 */
public class CrossFadingTransitionEffect implements AppStateTransitionEffect {
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
    public void effect(AppState from, AppState to, Listener transitionEffectListener) {

        SpriteInitializer spriteInitializer = from.getSpriteInitializer();

        if (!spriteInitializer.hasDeclared(Types.FADED_IN_RECTANGLE))
            declareFadedInRectangle(from, spriteInitializer, transitionEffectListener);
        if (!spriteInitializer.hasDeclared(Types.FADED_OUT_RECTANGLE))
            declareFadedOutRectangle(to, spriteInitializer, transitionEffectListener);

        from.getAppStateWorld().spawn(spriteInitializer.createSprite(Types.FADED_IN_RECTANGLE));
        to.getAppStateWorld().spawn(spriteInitializer.createSprite(Types.FADED_OUT_RECTANGLE));
    }

    private void declareFadedInRectangle(AppState from, SpriteInitializer spriteInitializer, Listener transitionEffectListener) {
        spriteInitializer.declare(Types.FADED_IN_RECTANGLE)
                .position(0, 0)
                .size(from.getGameWindowDefinition().size)
                .with(createFadedInRectangleFrameStateMachineComponent(from, transitionEffectListener))
                .commit();

    }

    private FrameStateMachineComponent createFadedInRectangleFrameStateMachineComponent(AppState from, Listener transitionEffectListener) {
        FrameStateMachineComponent frameStateMachineComponent = new FrameStateMachineComponent();
        RectangleFrame rectangleFrame = new RectangleFrame(0, 10, new Color(255, 255, 255, 0));
        EffectFrame rectangleEffectFrame = EffectFrame.wrap(rectangleFrame, 15);

        rectangleEffectFrame.addEffect((world, sprite) -> {
            Color color = rectangleFrame.getColor();
            if (color.getAlpha() == 0 && soundType != null)
                from.getSoundPlayer().playSound(soundType);
            int alpha = color.getAlpha() + 5 > 255 ? 255 : color.getAlpha() + 5;
            rectangleFrame.setColor(new Color(255, 255, 255, alpha));

            if (alpha == 255) {
                from.getAppStateWorld().removeSprite(rectangleEffectFrame.getSprite());
                transitionEffectListener.onFromEffectEnd();
            }
        });

        frameStateMachineComponent.addFrame(rectangleEffectFrame);
        frameStateMachineComponent.setCurrentFrame(rectangleEffectFrame);
        return frameStateMachineComponent;
    }


    private void declareFadedOutRectangle(AppState to, SpriteInitializer spriteInitializer, Listener transitionEffectListener) {
        spriteInitializer.declare(Types.FADED_OUT_RECTANGLE)
                .position(0, 0)
                .size(to.getGameWindowDefinition().size)
                .with(createFadedOutRectangleFrameStateMachineComponent(to, transitionEffectListener))
                .commit();

    }

    private FrameStateMachineComponent createFadedOutRectangleFrameStateMachineComponent(AppState to, Listener transitionEffectListener) {
        FrameStateMachineComponent frameStateMachineComponent = new FrameStateMachineComponent();
        RectangleFrame rectangleFrame = new RectangleFrame(0, 10, Color.white);
        EffectFrame rectangleEffectFrame = EffectFrame.wrap(rectangleFrame, 15);

        rectangleEffectFrame.addEffect((world, sprite) -> {
            Color color = rectangleFrame.getColor();
            int alpha = color.getAlpha() - 8 < 0 ? 0 : color.getAlpha() - 8;
            rectangleFrame.setColor(new Color(255, 255, 255, alpha));

            if (alpha == 0)
            {
                to.getAppStateWorld().removeSprite(rectangleEffectFrame.getSprite());
                transitionEffectListener.onToEffectEnd();
            }
        });

        frameStateMachineComponent.addFrame(rectangleEffectFrame);
        frameStateMachineComponent.setCurrentFrame(rectangleEffectFrame);
        return frameStateMachineComponent;
    }
}
