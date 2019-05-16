package com.pokewords.framework.sprites.parsing;

import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  @author nyngwang
 */
public interface Segment {
    Segment addElement(Element element);

    default @Nullable Element getElement(String elementName) {
        List<Element> elements = getElementsByName(elementName);
        return elements.isEmpty() ? null : elements.get(0);
    }

    List<Element> getElementsByName(String elementName);
    List<Element> getElements();

    boolean containsKey(String key);
    Segment put(String key, String value);
    Segment put(String key, int value);

    Optional<String> getStringByKeyOptional(String key);
    OptionalInt getIntByKeyOptional(String key);

    String getStringByKey(String key);
    Integer getIntByKey(String key);

    Segment setParentScript(Script parentScript);
    Script getParentScript();

    String toString(int indentation);
}
