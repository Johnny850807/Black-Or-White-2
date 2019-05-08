package com.pokewords.framework.sprites.components.marks;

import com.pokewords.framework.sprites.components.frames.Frame;

import java.util.Collection;

/**
 * Any component that can be rendered in some forms of Frame should implement this interface.
 * @author johnny850807 (waterball)
 */
public interface Renderable {
    Collection<? extends Frame> getAllFrames();
    Collection<? extends Frame> getRenderedFrames();
}
