package com.pokewords.framework.sprites.components.marks;

import com.pokewords.framework.sprites.components.ComponentMap;

/**
 * The marking interface signs a class as shareable,
 * this is used in Sprite#clone(), all components are defaulted as Cloneable,
 * this interface marks Components as shareable,
 * so that Sprite#clone() will share the Shareable components rather than clone them.
 *
 * @see ComponentMap#clone()
 * @author johnny850807
 */
public interface Shareable {
}
