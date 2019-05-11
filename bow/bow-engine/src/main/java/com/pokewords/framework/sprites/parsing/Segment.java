package com.pokewords.framework.sprites.parsing;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  基本上maps不要直接傳給client
 * @author nyngwang
 */
public interface Segment {
    // Elements management
    Segment addElement(Element element);
    List<Element> getElementsByName(String elementName);

    // Maps management
    Segment putKVPair(String key, String value);
    Segment putKVPair(String key, int value);
    Optional<String> getStringByKey(String key);
    Optional<Integer> getIntByKey(String key);

    // parentScript management
    Script getParentScript();
    Segment setParentScript(Script parentScript);

    // Not recommended
    List<Element> getElements();

}
