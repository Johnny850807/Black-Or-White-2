package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.KeyValuePairs;
import com.pokewords.framework.engine.exceptions.SegmentException;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  Each segment has a reference to its parent Script, possibly some Elements, and its key-value pairs.
 *  @author nyngwang
 */
public class LinScriptSegment extends Segment {

    private List<Element> elements;
    private KeyValuePairs keyValuePairs;
    private String name;
    private int id;
    private String description;

    public LinScriptSegment(String name, int id) {
        init();
        this.name = name;
        this.id = id;
    }

    public LinScriptSegment(String name, int id, String description) {
        init();
        this.name = name;
        this.id = id;
        this.description = description;
    }

    private void init() {
        elements = new ArrayList<>();
        keyValuePairs = new KeyValuePairs();
    }

    @Override
    public Segment addElement(Element element) {
        elements.add(element);
        element.setParent(this);
        return this;
    }

    @Override
    public boolean containsElement(String name) {
        for (Element element : elements)
            if (element.getName().equals(name))
                return true;
        return false;
    }

    @Override
    public List<Element> getElements(String name) {
        return elements.stream()
                .filter(element -> element.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<Element> getElements() {
        return elements;
    }

    @Override
    public Segment put(String key, String value) {
        keyValuePairs.stringMap.put(key, value);
        return this;
    }

    @Override
    public Segment put(String key, int value) {
        keyValuePairs.integerMap.put(key, value);
        return this;
    }

    @Override
    public Optional<String> getStringOptional(String key) {
        return Optional.ofNullable(keyValuePairs.stringMap.get(key));
    }

    @Override
    public OptionalInt getIntOptional(String key) {
        return keyValuePairs.integerMap.containsKey(key) ? OptionalInt.of(keyValuePairs.integerMap.get(key)) : OptionalInt.empty();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean containsKey(String key) {
        return keyValuePairs.integerMap.containsKey(key) || keyValuePairs.stringMap.containsKey(key);
    }

    @Override
    public String getString(String key) {
        String result = keyValuePairs.stringMap.get(key);
        if (result == null)
            throw new SegmentException(String.format("LinScriptElement: attribute %s does not exist", key));
        return result;
    }

    @Override
    public Integer getInt(String key) {
        Integer result = keyValuePairs.integerMap.get(key);
        if (result == null)
            throw new SegmentException(String.format("LinScriptElement: attribute %s does not exist", key));
        return result;
    }

    @Override
    public Segment setParent(Script parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public Script getParent() {
        return parent;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public String toString(int indentation) {
        StringBuilder resultBuilder = new StringBuilder();
        String indent = new String(new char[indentation]).replace("\0", " ");
        resultBuilder.append(String.format("<%s> %d %s\n", getName(), getId(), getDescription() == null? "" : getDescription()));
        keyValuePairs.stringMap.forEach((key, value) -> resultBuilder.append(String.format(indent + "%s: %s\n", key, value)));
        keyValuePairs.integerMap.forEach((key, value) -> resultBuilder.append(String.format(indent + "%s: %s\n", key, value)));
        elements.forEach(element ->
                resultBuilder.append(element
                        .toString(indentation)
                        .replaceAll("(.*?\n)", indent + "$1")));
        resultBuilder.append(String.format("</%s>\n", getName()));
        return resultBuilder.toString();
    }

    @Override
    public String toString() {
        return toString(4);
    }

    public static void main(String[] args) {
        Segment segment = new LinScriptSegment("frame", 1, "punch")
                .addElement(new LinScriptElement("bow"))
                .addElement(new LinScriptElement("cow"));
        System.out.println(segment);

    }
}
