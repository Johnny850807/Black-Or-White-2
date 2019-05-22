package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.KeyValuePairs;
import com.pokewords.framework.engine.exceptions.ElementException;

import java.util.*;

/**
 * @author nyngwang
 */
public class LinScriptElement extends Element {

    public LinScriptElement(String name) {
        super(name, Integer.MIN_VALUE, null);
    }

    public LinScriptElement() {
        this(null);
    }

    @Override
    public void parse(Context context) {
        // hasn't fetch
        if (context.isEmpty())
            context.fetchNextToken();
        c
    }

    @Override
    public String toString(int indentation) {
        StringBuilder resultBuilder = new StringBuilder();
        String indent = new String(new char[indentation]).replace("\0", " ");
        resultBuilder.append(String.format("<%s>\n", getName()));
        resultBuilder.append(keyValuePairsToString(indentation));
        resultBuilder.append(String.format("</%s>\n", getName()));
        return resultBuilder.toString();
    }
}
