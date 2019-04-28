package com.pokewords.framework.sprites.parsing;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nyngwang
 */
public class Segment {
    public static final String ID = "segment-id";
    public static final String DESCRIPTION = "segment-description";

    private Script parentScript;
    private ArrayList<Element> elements;

    public Segment(Script parentScript) {
        this.parentScript = parentScript;
        parentScript.getRules().validSegmentKVRules.get("");

    }

    Class<parentScript.getRules().>

    // Fluent key-value pair g/setter

    Segment addKeyValuePair(String key, String value);
    Segment addKeyValuePair(String key, int value);
    String getStringValueByKey(String key);
    int getIntValueByKey(String key);

    //

    void addElement(Element element);
    List<Element> getElements();

    String toString();

}
