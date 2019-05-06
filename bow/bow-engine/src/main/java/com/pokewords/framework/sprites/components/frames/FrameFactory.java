package com.pokewords.framework.sprites.components.frames;


import com.pokewords.framework.sprites.parsing.FrameSegment;

public interface FrameFactory {
    Frame createFrame(FrameSegment frameSegment);
}
