package com.pokewords.framework.sprites.parsing;

import java.util.Collection;
import java.util.Optional;
import java.util.OptionalInt;


/**
 * An Element can contain: key-value pairs, name, parent.
 * @author nyngwang
 */
public abstract class Element extends Node {
    String toString(int indentation);
}
