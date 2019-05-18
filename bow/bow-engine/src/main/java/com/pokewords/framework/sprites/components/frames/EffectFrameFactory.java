package com.pokewords.framework.sprites.components.frames;


import com.pokewords.framework.sprites.parsing.Segment;

public interface EffectFrameFactory {
    EffectFrame createFrame(Segment frameSegment);
}
