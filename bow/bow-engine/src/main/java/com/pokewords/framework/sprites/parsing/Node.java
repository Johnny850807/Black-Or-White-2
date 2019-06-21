package com.pokewords.framework.sprites.parsing;

/**
 * @author nyngwang
 */
public interface Node {
    // should check the first token correct before call.
    void parse(Context context);

    // Only apply indent to Node content, doesn't include the wrapper.
    String toString(int indent);

    void setParent(Node parent);

    Node getParent();
}
