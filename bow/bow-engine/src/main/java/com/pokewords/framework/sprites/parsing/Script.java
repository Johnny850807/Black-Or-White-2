package com.pokewords.framework.sprites.parsing;


import java.util.Collection;

/**
 * @author nyngwang
 */
public abstract class Script implements Node {
    public abstract Collection<? extends Node> getNodes();
}
