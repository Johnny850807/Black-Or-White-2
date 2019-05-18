package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.SegmentException;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.pokewords.framework.sprites.parsing.ScriptDefinitions.LinScript.Segment.*;

/**
 *  Each segment has a reference to its parent Script, possibly some Elements, and its key-value pairs.
 *  @author nyngwang
 */
public class LinScriptSegment implements Segment {
    private LinScript parentScript;
    private List<Element> elements;
    private Script.Mappings mappings;
    private String segmentName;
    private int segmentId;
    private String segmentDescription;

    public LinScriptSegment(String segmentName, int segmentId) {
        init();
        this.segmentName = segmentName;
        this.segmentId = segmentId;
    }

    public LinScriptSegment(String segmentName, int segmentId, String segmentDescription) {
        init();
        this.segmentName = segmentName;
        this.segmentId = segmentId;
        this.segmentDescription = segmentDescription;
    }

    private void init() {
        elements = new ArrayList<>();
        mappings = new Script.Mappings();
    }

    @Override
    public Segment addElement(Element element) {
        elements.add(element);
        element.setParentSegment(this);
        return this;
    }

    @Override
    public List<Element> getElementsByName(String elementName) {
        return elements.stream()
                .filter(element ->
                        element.getStringByKey(ScriptDefinitions.LinScript.Element.NAME)
                               .equals(elementName))
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
    public String getSegmentName() {
        return getStringByKey(NAME);
    }

    @Override
    public String getSegmentDescription() {
        return getStringByKey(DESCRIPTION);
    }

    @Override
    public int getId() {
        return getIntByKey(ID);
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
    public Segment setParentScript(Script parentScript) {
        this.parentScript = (LinScript) parentScript;
        return this;
    }

    @Override
    public LinScript getParentScript() {
        return parentScript;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public String toString(int indentation) {
        StringBuilder resultBuilder = new StringBuilder();
        String indent = new String(new char[indentation]).replace("\0", " ");
        resultBuilder.append(String.format("<%s> %s %s\n",
                getStringByKey(NAME), getIntByKey(ID), getStringByKeyOptional(DESCRIPTION).orElse("")));
        mappings.stringMap.forEach((key, value) -> resultBuilder.append(String.format(indent + "%s: %s\n", key, value)));
        mappings.integerMap.forEach((key, value) -> resultBuilder.append(String.format(indent + "%s: %s\n", key, value)));
        elements.forEach(element ->
                resultBuilder.append(element
                        .toString(indentation)
                        .replaceAll("(.*?\n)", indent + "$1")));
        resultBuilder.append(String.format("</%s>\n", getStringByKey(NAME)));
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
