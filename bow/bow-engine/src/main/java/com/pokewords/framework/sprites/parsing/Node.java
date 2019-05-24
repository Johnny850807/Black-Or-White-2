package com.pokewords.framework.sprites.parsing;

/**
 * @author nyngwang
 */
public interface Node {
    Node getParent();

    // Should check context.hasNextToken() before call.
    // Do nothing if mismatch at first token.
    void parse(Context context);

    String toString(int indent);
}
