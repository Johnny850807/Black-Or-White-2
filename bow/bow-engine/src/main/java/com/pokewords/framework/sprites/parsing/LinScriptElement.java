package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.ElementException;

import java.util.Map;
import java.util.Optional;

/**
 * @author nyngwang
 */
public class LinScriptElement implements Element {
    private LinScriptSegment parentSegment;
    private Script.Mappings mappings;

    public LinScriptElement(String elementName) {
        init();
        mappings.stringMap.put(ScriptDefinitions.LinScript.Element.NAME, elementName);
    }

    private void init() {
        parentSegment = null;
        mappings = new Script.Mappings();
    }

    @Override
    public Element put(String key, String value) {
        mappings.stringMap.put(key, value);
        return this;
    }

    @Override
    public Element put(String key, int value) {
        mappings.integerMap.put(key, value);
        return this;
    }

    @Override
    public boolean containsKey(String key) {
        return mappings.integerMap.containsKey(key) || mappings.stringMap.containsKey(key);
    }

    @Override
    public Optional<String> getStringByKeyOptional(String key) {
        return Optional.ofNullable(mappings.stringMap.get(key));
    }

    @Override
    public Optional<Integer> getIntByKeyOptional(String key) {
        return Optional.ofNullable(mappings.integerMap.get(key));
    }

    @Override
    public String getStringByKey(String key) {
        String result = mappings.stringMap.get(key);
        if (result == null)
            throw new ElementException(String.format("LinScriptElement: attribute %s does not exist", key));
        return result;
    }

    @Override
    public Integer getIntByKey(String key) {
        Integer result = mappings.integerMap.get(key);
        if (result == null)
            throw new ElementException(String.format("LinScriptElement: attribute %s does not exist", key));
        return result;
    }

    @Override
    public Segment getParentSegment() {
        return parentSegment;
    }

    @Override
    public Element setParentSegment(Segment parentSegment) {
        this.parentSegment = (LinScriptSegment) parentSegment;
        return this;
    }


    // Pretty print
    @Override
    public String toString(int indentation) {
        StringBuilder resultBuilder = new StringBuilder();
        String indent = ""; for (int i = 1; i<=indentation; i++) indent += " ";
        resultBuilder
                .append("<").append(mappings.stringMap.get(ScriptDefinitions.LinScript.Element.NAME)).append(">").append('\n');
        for (Map.Entry<String, String> entry : mappings.stringMap.entrySet()) {
            resultBuilder
                    .append(indent).append(entry.getKey())
                    .append(" ").append(entry.getValue()).append('\n');
        }
        for (Map.Entry<String, Integer> entry : mappings.integerMap.entrySet()) {
            resultBuilder
                    .append(indent).append(entry.getKey())
                    .append(" ").append(entry.getValue()).append('\n');
        }
        resultBuilder
                .append("</").append(mappings.stringMap.get(ScriptDefinitions.LinScript.Element.NAME)).append(">");
        return resultBuilder.toString();
    }

    @Override
    public String toString() {
        return toString(4);
    }
}
