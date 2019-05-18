package com.pokewords.framework.sprites.parsing;

import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 *  @author nyngwang
 */
public interface Segment {

    default @Nullable Element getElementByName(String name) {
        List<Element> elements = getElementsByName(name);
        return elements.isEmpty() ? null : elements.get(0);
    }

    Segment addElement(Element element);
    List<Element> getElementsByName(String name);
    List<Element> getElements();

    Segment put(String key, String value);
    Segment put(String key, int value);

    String getName();
    int getId();
    String getDescription();

    Optional<String> getStringByKeyOptional(String key);
    OptionalInt getIntByKeyOptional(String key);
    boolean containsKey(String key);
    String getStringByKey(String key);
    Integer getIntByKey(String key);

    Segment setParent(Script parent);
    Script getParent();

    String toString(int indentation);
}
