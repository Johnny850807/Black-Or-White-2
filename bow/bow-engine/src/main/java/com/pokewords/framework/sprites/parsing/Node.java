package com.pokewords.framework.sprites.parsing;

/**
 * @author nyngwang
 */
public interface Node {
    // should check the first token correct before call.
    void parse(Context context);

    String toString(int contentIndent);

    void setParent(Node parent);

    Node getParent();
}
