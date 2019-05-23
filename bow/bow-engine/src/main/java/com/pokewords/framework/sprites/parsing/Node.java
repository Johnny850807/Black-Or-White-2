package com.pokewords.framework.sprites.parsing;

/**
 * @author nyngwang
 */
public interface Node {
    Node getParent();

    void parse(Context context);

    String toString(int indent);
}
