package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.ElementNameDoesNotExistException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrameSegment {

    public final static String PIC = "pic";
    public final static String DURATION = "duration";
    public final static String NEXT = "next";

    private Script script;

    // Segment Info
    private int frameNumber;
    private String frameName;

    // Attributes
    private Map<String, Element> elementMap;
    private Map<String, Integer> intMap;
    private Map<String, String> stringMap;

    public FrameSegment(Script script, int frameNumber, String frameName) {
        this.script = script;
        this.frameNumber = frameNumber;
        this.frameName = frameName;
        elementMap = new HashMap<>();
        intMap = new HashMap<>();
        stringMap = new HashMap<>();
    }


    // Fluent Setters
    public FrameSegment addPair(String key, int val) {
        intMap.put(key, val);
        return this;
    }

    public FrameSegment addPair(String key, String val) {
        stringMap.put(key, val);
        return this;
    }

    public FrameSegment addElement(String name, Element element) {
        elementMap.put(name, element);
        return this;
    }

    // Get attributes

    public Element getElement(String name){
        if (!elementMap.containsKey(name)) {
            throw new ElementNameDoesNotExistException(
                    "The element with specified name does not exist!"
            );
        }
        return elementMap.get(name);
    }

    public String getString(String name){
        if (!stringMap.containsKey(name)) {
            throw new ElementNameDoesNotExistException(
                    "The string with specified name does not exist!"
            );
        }
        return stringMap.get(name);
    }

    public int getInt(String name){
        if (!intMap.containsKey(name)) {
            throw new ElementNameDoesNotExistException(
                    "The integer with specified name does not exist!"
            );
        }
        return intMap.get(name);
    }

    // Getters

    public int getFrameNumber(){
        return frameNumber;
    }

    public String getFrameName() {
        return frameName;
    }

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

}
