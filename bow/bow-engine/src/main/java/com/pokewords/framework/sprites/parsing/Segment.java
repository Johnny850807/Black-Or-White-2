package com.pokewords.framework.sprites.parsing;

import java.util.List;

public interface Segment {

    // 不能改的就不需要setter

    String getSegmentId();

    String getSegmentDescription();


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
