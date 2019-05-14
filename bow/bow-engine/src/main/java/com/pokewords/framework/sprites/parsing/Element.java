package com.pokewords.framework.sprites.parsing;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 *  @author nyngwang
 */
public interface Element {
    Element put(String key, String value);
    Element put(String key, int value);
    Optional<String> getStringByKey(String key);
    Optional<Integer> getIntByKey(String key);

    Element setParentSegment(Segment parentSegment);
    Segment getParentSegment();

    String toString(int indentation);
}
