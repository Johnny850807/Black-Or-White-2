package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.SegmentException;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  Each segment has a reference to its parent Script, possibly some Elements, and its key-value pairs.
 *  @author nyngwang
 */
public class LinScriptSegment implements Segment {
    private Script parent;
    private List<Element> elements;
    private Script.Mappings mappings;
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
        mappings = new Script.Mappings();
    }

    @Override
    public Segment addElement(Element element) {
        elements.add(element);
        element.setParent(this);
        return this;
    }

    @Override
    public List<Element> getElementsByName(String name) {
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
        mappings.stringMap.put(key, value);
        return this;
    }

    @Override
    public Segment put(String key, int value) {
        mappings.integerMap.put(key, value);
        return this;
    }

    @Override
    public Optional<String> getStringByKeyOptional(String key) {
        return Optional.ofNullable(mappings.stringMap.get(key));
    }

    @Override
    public OptionalInt getIntByKeyOptional(String key) {
        return mappings.integerMap.containsKey(key) ? OptionalInt.of(mappings.integerMap.get(key)) : OptionalInt.empty();
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
        return mappings.integerMap.containsKey(key) || mappings.stringMap.containsKey(key);
    }

    @Override
    public String getStringByKey(String key) {
        String result = mappings.stringMap.get(key);
        if (result == null)
            throw new SegmentException(String.format("LinScriptElement: attribute %s does not exist", key));
        return result;
    }

    @Override
    public Integer getIntByKey(String key) {
        Integer result = mappings.integerMap.get(key);
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
        mappings.stringMap.forEach((key, value) -> resultBuilder.append(String.format(indent + "%s: %s\n", key, value)));
        mappings.integerMap.forEach((key, value) -> resultBuilder.append(String.format(indent + "%s: %s\n", key, value)));
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
