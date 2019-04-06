package com.pokewords.framework.engine;

import com.pokewords.framework.sprites.components.Frame;

public interface FrameFactory {
    Frame createFrame(String name);
}
