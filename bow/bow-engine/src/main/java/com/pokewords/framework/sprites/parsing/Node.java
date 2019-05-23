package com.pokewords.framework.sprites.parsing;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * A Node provides some basic data structures / data.
 * @author nyngwang
 */
public interface Node {
    Node getParent();

    void parse(Context context);

    String toString(int indent);
}
