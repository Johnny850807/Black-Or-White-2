package com.pokewords.framework.sprites.parsing;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 *  基本上maps不要直接傳給client
 * @author nyngwang
 */
public class Element {
    public static void main(String[] args) {
        Element element = new Element("bow");
        element.putKVPair("hey", 123);
        element.putKVPair("hey2", 456);
        element.putKVPair("yo", "yo");
        element.putKVPair("yoho", "yoho");
        System.out.println(element);
    }

    public interface Def {
        String NAME = "name";
    }
    private static class Maps {
        private Map<String, String> stringMap;
        private Map<String, Integer> integerMap;
        private Maps() {
            stringMap = new HashMap<>();
            integerMap = new HashMap<>();
        }
    }
    private Segment parentSegment;
    private Maps maps;

    public Element(String elementName) {
        init();
        maps.stringMap.put(Def.NAME, elementName);
    }

    private void init() {
        maps = new Maps();
    }

    // Maps management

    public Element putKVPair(String key, String value) {
        maps.stringMap.put(key, value);
        return this;
    }

    public Element putKVPair(String key, int value) {
        maps.integerMap.put(key, value);
        return this;
    }

    public Optional<String> getStringByKey(String key) {
        return Optional.of(maps.stringMap.get(key));
    }

    public Optional<Integer> getIntByKey(String key) {
        return Optional.of(maps.integerMap.get(key));
    }

    // parentSegment management

    public Segment getParentSegment() {
        return parentSegment;
    }

    public void setParentSegment(Segment parentSegment) { this.parentSegment = parentSegment; }

    @Override
    public String toString() {
        return toString(4);
    }

    public String toString(int indentation) {
        StringBuilder resultBuilder = new StringBuilder();
        String indent = ""; for (int i = 1; i<=indentation; i++) indent += " ";
        resultBuilder.append("<" + maps.stringMap.get(Def.NAME) + ">").append("\\n");
        for (Map.Entry<String, String> entry : maps.stringMap.entrySet()) { resultBuilder.append(indent + entry.getKey() + " " + entry.getValue() + "\\n"); }
        for (Map.Entry<String, Integer> entry : maps.integerMap.entrySet()) { resultBuilder.append(indent + entry.getKey() + " " + entry.getValue() + "\\n"); }
        resultBuilder.append("</" + maps.stringMap.get(Def.NAME) + ">").append("\\n");
        return resultBuilder.toString();
    }
}
