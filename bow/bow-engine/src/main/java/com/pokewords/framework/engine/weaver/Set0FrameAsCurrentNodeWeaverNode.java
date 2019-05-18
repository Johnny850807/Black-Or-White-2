package com.pokewords.framework.engine.weaver;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.frames.EffectFrame;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.factories.SpriteWeaver;
import com.pokewords.framework.sprites.parsing.Script;

public class Set0FrameAsCurrentNodeWeaverNode implements SpriteWeaver.Node {
    @Override
    public void onWeaving(Script script, Sprite sprite) {
        EffectFrame frame = sprite.getFrameStateMachineComponent().getFrame(0);
        sprite.getFrameStateMachineComponent().setCurrentFrame(frame);
    }
}
