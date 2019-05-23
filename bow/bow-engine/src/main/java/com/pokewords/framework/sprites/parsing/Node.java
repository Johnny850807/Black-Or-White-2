package com.pokewords.framework.sprites.parsing;

/**
 * @author nyngwang
 */
public interface Node {
    Node getParent();

    boolean parse(Context context);

    String toString(int indent);
}
