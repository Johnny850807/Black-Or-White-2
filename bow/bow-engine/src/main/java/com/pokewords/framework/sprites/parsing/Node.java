package com.pokewords.framework.sprites.parsing;

/**
 * @author nyngwang
 */
public interface Node {
    Node getParent();

    // It will check whether context is run out of tokens.
    void parse(Context context);

    String toString(int indent);
}
