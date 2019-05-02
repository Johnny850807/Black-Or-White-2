package com.pokewords.framework.sprites.components;

import java.util.Collection;

/**
 * Any component that can be rendered in some forms of Frame should implement this interface.
 * @author johnny850807 (waterball)
 */
public interface Renderable {
    Collection<Frame> getRenderedFrames();
}
