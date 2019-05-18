package com.pokewords.framework.sprites.parsing;

import java.util.Optional;
import java.util.OptionalInt;


/**
 *  @author nyngwang
 */
public interface Element {
    Element put(String key, String value);
    Element put(String key, int value);

    String getElementName();

    Optional<String> getStringByKeyOptional(String key);
    OptionalInt getIntByKeyOptional(String key);
    boolean containsKey(String key);
    String getStringByKey(String key);
    Integer getIntByKey(String key);

    Element setParentSegment(Segment parentSegment);
    Segment getParentSegment();

    String toString(int indentation);
}
