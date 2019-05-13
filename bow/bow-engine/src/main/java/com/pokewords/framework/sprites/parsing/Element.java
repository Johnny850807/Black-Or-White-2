package com.pokewords.framework.sprites.parsing;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 *  基本上maps不要直接傳給client
 * @author nyngwang
 */
public interface Element {
    // Maps management
    Element putKVPair(String key, String value);
    Element putKVPair(String key, int value);
    Optional<String> getStringByKey(String key);
    Optional<Integer> getIntByKey(String key);

    // parentSegment management
    Segment getParentSegment();
    Element setParentSegment(Segment parentSegment);

    // print
    String toString(int indentation);
}
