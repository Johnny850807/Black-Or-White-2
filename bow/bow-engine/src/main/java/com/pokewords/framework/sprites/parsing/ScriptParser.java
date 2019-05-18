package com.pokewords.framework.sprites.parsing;

/**
 * @author nyngwang
 */
public interface ScriptParser {
    Script parse(String scriptText, ScriptRules scriptRules);
}
