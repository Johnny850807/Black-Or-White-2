package com.pokewords.framework.sprites.parsing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author nyngwang
 * @deprecated true;
 */
public class LinScriptRules implements ScriptRules {

    private Set<String> validSegmentNames;
    private Map<String, Pair> validSegmentKVRules;
    private Set<String> validElementNames;
    private Map<String, Pair> validElementKVRules;

    public LinScriptRules() {
        validSegmentNames = new HashSet<>();
        validSegmentKVRules = new HashMap<>();
        validElementNames = new HashSet<>();
        validElementKVRules = new HashMap<>();
    }

    @Override
    public Set<String> getValidSegmentNames() {
        return validSegmentNames;
    }

    @Override
    public Map<String, Pair> getValidSegmentKVRules() {
        return validSegmentKVRules;
    }

    @Override
    public Set<String> getValidElementNames() {
        return validElementNames;
    }

    @Override
    public Map<String, Pair> getValidElementKVRules() {
        return validElementKVRules;
    }
}
