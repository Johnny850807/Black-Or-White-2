package com.pokewords.framework.sprites.parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author nyngwang
 */
public class Segment {
    public static final String ID = "segment-id";
    public static final String DESCRIPTION = "segment-description";

    private Script parentScript;
    private ArrayList<Element> elements;
    private Map<String, Class> test;
    private Map<String, String> test2;

    public Segment(Script parentScript) {
        this.parentScript = parentScript;
    }

    public void test() {
        Object o = new Object();
        parentScript.getRules().validSegmentKVRules.get("");
        try {
            Class cls;
            test.get("element").cast(test2.get("element"));
        } catch(Exception e) {

        }
    }

    public Class<Map> void func() {
        return null;
    }

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
