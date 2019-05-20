package com.pokewords.framework.sprites.parsing;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.OptionalInt;


/**
 *  @author nyngwang
 */
public interface Element {
    Element put(String key, String value);
    Element put(String key, int value);

    String getName();

    Optional<String> getStringByKeyOptional(String key);
    OptionalInt getIntByKeyOptional(String key);
    boolean containsKey(String key);
    String getStringByKey(String key);
    Integer getIntByKey(String key);

    Collection<String> getKeys();

    Element setParent(Segment parentSegment);
    Segment getParent();

    String toString(int indentation);
}
