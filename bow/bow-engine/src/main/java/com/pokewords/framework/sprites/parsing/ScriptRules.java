package com.pokewords.framework.sprites.parsing;

import java.util.Map;
import java.util.Set;

/**
 * @author nyngwang
 */
public interface ScriptRules {
    class Pair {
        public String regex;
        public String type;
        public Pair(String regex, String type) {
            this.regex = regex;
            this.type = type;
        }
    }
    Set<String> getValidSegmentNames();
    Map<String, Pair> getValidSegmentKVRules();
    Set<String> getValidElementNames();
    Map<String, Pair> getValidElementKVRules();
}
