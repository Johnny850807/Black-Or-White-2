package com.pokewords.framework.sprites.parsing;

import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 *  @author nyngwang
 */
public interface Segment {

    default @Nullable Element getElementByName(String elementName) {
        List<Element> elements = getElementsByName(elementName);
        return elements.isEmpty() ? null : elements.get(0);
    }

    Segment addElement(Element element);
    List<Element> getElementsByName(String elementName);
    List<Element> getElements();

    Segment put(String key, String value);
    Segment put(String key, int value);

    String getSegmentName();
    String getSegmentDescription();
    int getId();

    Optional<String> getStringByKeyOptional(String key);
    OptionalInt getIntByKeyOptional(String key);
    boolean containsKey(String key);
    String getStringByKey(String key);
    Integer getIntByKey(String key);

    Segment setParentScript(Script parentScript);
    Script getParentScript();

    String toString(int indentation);
}
