package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.components.marks.Renderable;

import java.util.Collection;

public class TerrainMapComponent extends CloneableComponent implements Renderable {
    @Override
    public Collection<? extends Frame> getAllFrames() {
        return null;
    }

    @Override
    public Collection<? extends Frame> getRenderedFrames() {
        return null;
    }
}
