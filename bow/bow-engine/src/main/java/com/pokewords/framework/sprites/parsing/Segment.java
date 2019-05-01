package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.utils.FileUtility;

import javax.print.DocFlavor;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  基本上maps不要直接傳給client
 * @author nyngwang
 */
public class Segment {
    public interface Def {
        String NAME = "segment-name";
        String ID = "segment-id";
        String DESCRIPTION = "segment-description";
    }
    private static class Maps {
        private Map<String, String> stringMap;
        private Map<String, Integer> integerMap;
        private Maps() {
            stringMap = new HashMap<>();
            integerMap = new HashMap<>();
        }
    }
    private Script parentScript;
    private ArrayList<Element> elements;
    private Maps maps;

    public Segment(String segmentName, int segmentId) {
        init(segmentName, segmentId);
    }

    // segmentDescription == null時等同做 Segment(name, id)
    public Segment(String segmentName, int segmentId, String segmentDescription) {
        init(segmentName, segmentId);
        maps.stringMap.put(Def.DESCRIPTION, segmentDescription);
    }

    private void init(String segmentName, int segmentId) {
        maps.stringMap.put(Def.NAME, segmentName);
        maps.integerMap.put(Def.ID, segmentId);
        elements = new ArrayList<>();
        maps = new Maps();
    }

    // Elements management

    public Segment addElement(Element element) {
        elements.add(element);
        element.setParentSegment(this);
        return this;
    }

    public List<Element> getElementsByName(String elementName) {
        return elements.stream()
                .filter(element -> element.getStringByKey(Element.Def.NAME).equals(elementName))
                .collect(Collectors.toList());
    }

    // Maps management

    public Segment putKVPair(String key, String value) {
        maps.stringMap.put(key, value);
        return this;
    }

    public Segment putKVPair(String key, int value) {
        maps.integerMap.put(key, value);
        return this;
    }

    public Optional<String> getStringByKey(String key) {
        return Optional.of(maps.stringMap.get(key));
    }

    public Optional<Integer> getIntByKey(String key) {
        return Optional.of(maps.integerMap.get(key));
    }

    // parentScript management

    public Script getParentScript() {
        return parentScript;
    }

    public void setParentScript(Script parentScript) { this.parentScript = parentScript; }

    // Not recommended

    public List<Element> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        return toString(4);
    }

    public String toString(int indentation) {
        StringBuilder resultBuilder = new StringBuilder();
        String indent = ""; for (int i = 1; i<=indentation; i++) indent += " ";
        resultBuilder.append("<" + maps.stringMap.get(Def.NAME) + ">")
               .append(" " + maps.integerMap.get(Def.ID))
               .append(" " + maps.stringMap.get(Def.DESCRIPTION)).append("\\n");
        for (Map.Entry<String, String> entry : maps.stringMap.entrySet()) { resultBuilder.append(indent + entry.getKey() + " " + entry.getValue() + "\\n"); }
        for (Map.Entry<String, Integer> entry : maps.integerMap.entrySet()) { resultBuilder.append(indent + entry.getKey() + " " + entry.getValue() + "\\n"); }
        for (Element element : elements) { resultBuilder.append(indent + element.toString(indentation)); }
        resultBuilder.append("</" + maps.stringMap.get(Def.NAME) + ">").append("\\n");
        return resultBuilder.toString();
    }
}
