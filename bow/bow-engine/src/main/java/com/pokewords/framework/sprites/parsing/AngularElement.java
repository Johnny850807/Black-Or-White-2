package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.bundles.ReadOnlyBundle;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * @author nyngwang
 */
public class AngularElement extends Element {
    private KeyValuePairs keyValuePairs = new NoCommaPairs();

    public AngularElement() {
        keyValuePairs.setParent(this);
    }

    public AngularElement(String name) {
        super(name);
        keyValuePairs.setParent(this);
    }

    @Override
    public void parse(Context context) {
        String openTag = context.fetchNextToken(
                "<[^/\\s]\\S+>",
                "Invalid open tag: " + context.peekToken());
        setName(openTag.replaceAll("<(\\S+)>", "$1"));
        if (context.peekToken().matches("[^\\s:<>\\[\\]]+"))
            keyValuePairs.parse(context);
        String closeTag = context.fetchNextToken(
                "</" + getName() + ">",
                "Expect </" + getName() + "> but get: " + context.peekToken());
    }

    @Override
    public String toString(int indent) {
        StringBuilder resultBuilder = new StringBuilder();
        String spaces = new String(new char[indent]).replace("\0", " ");
        resultBuilder.append(String.format("<%s>\n", getName()));
        resultBuilder.append(keyValuePairs.toString(indent).replaceAll("([^\n]*\n)", spaces + "$1"));
        resultBuilder.append(String.format("</%s>\n", getName()));
        return resultBuilder.toString();
    }

    @Override
    public KeyValuePairs getKeyValuePairs() {
        return keyValuePairs;
    }

    @Override
    public int getInt(String key) {
        return keyValuePairs.getInt(key);
    }

    @Override
    public String getString(String key) {
        return keyValuePairs.getString(key);
    }

    @Override
    public ReadOnlyBundle pack() {
        return keyValuePairs.pack();
    }

    @NotNull
    @Override
    public Iterator<Pair<String, String>> iterator() {
        return keyValuePairs.iterator();
    }
}
