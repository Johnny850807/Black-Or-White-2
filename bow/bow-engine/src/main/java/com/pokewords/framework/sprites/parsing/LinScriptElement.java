package com.pokewords.framework.sprites.parsing;

import java.util.Map;
import java.util.Optional;

public class LinScriptElement implements Element {
    private LinScriptSegment parentSegment;
    private Script.Mappings mappings;

    public LinScriptElement(String elementName) {
        init();
        mappings.stringMap.put(ScriptDef.LinScript.Element.NAME, elementName);
    }

    private void init() {
        mappings = new Script.Mappings();
    }

    @Override
    public Element putKVPair(String key, String value) {
        mappings.stringMap.put(key, value);
        return this;
    }

    @Override
    public Element putKVPair(String key, int value) {
        mappings.integerMap.put(key, value);
        return this;
    }

    @Override
    public Optional<String> getStringByKey(String key) {
        return Optional.of(mappings.stringMap.get(key));
    }

    @Override
    public Optional<Integer> getIntByKey(String key) {
        return Optional.of(mappings.integerMap.get(key));
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
                .append("<").append(mappings.stringMap.get(ScriptDef.LinScript.Element.NAME)).append(">").append('\n');
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
                .append("</").append(mappings.stringMap.get(ScriptDef.LinScript.Element.NAME)).append(">");
        return resultBuilder.toString();
    }

    @Override
    public String toString() {
        return toString(4);
    }
}
