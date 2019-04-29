package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.ElementAttributeNameDoesNotExistException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 *  基本上maps不要直接傳給client
 * @author nyngwang
 */
public class Element {
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
        // TODO: Pretty print
        return null;
    }
}
