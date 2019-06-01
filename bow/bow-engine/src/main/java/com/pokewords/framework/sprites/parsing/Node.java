package com.pokewords.framework.sprites.parsing;

/**
 * @author nyngwang
 */
public interface Node {
    Node getParent();

    // should check the first token correct before call.
    void parse(Context context);

    String toString(int indent);

    void setParent(Node parent);
}
