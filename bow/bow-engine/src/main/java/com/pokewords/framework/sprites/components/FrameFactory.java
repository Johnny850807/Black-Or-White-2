package com.pokewords.framework.sprites.components;


import com.pokewords.framework.sprites.parsing.FrameSegment;

public interface FrameFactory {
    Frame createFrame(FrameSegment frameSegment);
}
