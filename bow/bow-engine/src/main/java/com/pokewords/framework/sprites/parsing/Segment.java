package com.pokewords.framework.sprites.parsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nyngwang
 */
public class Segment {
    private static class Maps {
        public Map<String, String> stringMap;
        public Map<String, Integer> integerMap;
        public Maps() {
            stringMap = new HashMap<>();
            integerMap = new HashMap<>();
        }
    }
    public static final String ID = "segment-id";
    public static final String DESCRIPTION = "segment-description";

    private Script parentScript;
    private ArrayList<Element> elements;
    private Maps maps;

    public Segment(Script parentScript) {
        this.parentScript = parentScript;
        maps = new Maps();
    }

    // Fluent key-value pair g/setter

    Segment addKeyValuePair(String key, String value);
    Segment addKeyValuePair(String key, int value);
    String getStringByKey(String key);
    int getIntByKey(String key);

    //

    void addElement(Element element);
    List<Element> getElements();

    String toString();

}
