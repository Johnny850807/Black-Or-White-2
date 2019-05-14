package com.pokewords.framework.sprites.parsing;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  @author nyngwang
 */
public interface Segment {
    Segment addElement(Element element);
    List<Element> getElementsByName(String elementName);
    List<Element> getElements();

    Segment put(String key, String value);
    Segment put(String key, int value);
    String getStringByKey(String key);
    Integer getIntByKey(String key);

    Segment setParentScript(Script parentScript);
    Script getParentScript();

    String toString(int indentation);
}
