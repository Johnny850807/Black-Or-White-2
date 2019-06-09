package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.bundles.ReadOnlyBundle;
import com.pokewords.framework.engine.exceptions.NodeException;
import com.pokewords.framework.engine.exceptions.ScriptParsingException;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author nyngwang
 */
public class AngularSegment extends Segment {
    private KeyValuePairs keyValuePairs = new NoCommaPairs();
    private List<Element> elements = new ArrayList<>();

    public AngularSegment() {}

    public AngularSegment(String name, int id, @Nullable String description) {
        super(name, id, description);
    }

    @Override
    public void parse(Context context) {
        String openTag = context.fetchNextToken(
                "<[^/\\s]\\S+>", "Invalid open tag: " + context.peekToken());
        setName(openTag.replaceAll("<(\\S+)>", "$1"));

        String id = context.fetchNextToken("0|[1-9]\\d*", "Invalid id: " + context.peekToken());
        setId(Integer.parseInt(id));

        String at1AfterId = context.fetchNextToken();
        if (at1AfterId.equals(getCloseTag())) {
            setDescription(null);
            return;
        }

        String at2AfterId = context.fetchNextToken();
        if (at2AfterId.equals(getCloseTag())) {
            setDescription(at1AfterId);
            return;
        }

        // No description: (1) key : (2) <element> ?
        if (at1AfterId.matches("<[^/\\s]\\S+>") || at2AfterId.matches(":")) {
            setDescription(null);
            context.putBack(at2AfterId);
            context.putBack(at1AfterId);
        } else { // Has description: (1) desc key (2) desc <element>
            setDescription(at1AfterId);
            context.putBack(at2AfterId);
        }

        do {
            if (context.peekToken().equals(getCloseTag())) {
                context.consumeOneToken();
                return;
            }
            if (context.peekToken().matches("<[^/\\s]\\S+>")) { // It's element
                Element element = new AngularElement();
                element.parse(context);
                addElement(element);
                continue;
            }
            if (context.peekToken().matches("[^\\s:<>]+")) { //It's key
                getKeyValuePairs().parse(context);
                continue;
            }
            throw new ScriptParsingException("Segment body contains something neither key-value pair nor element.");
        } while (true);
    }

    @Override
    public String toString(int indent) {
        StringBuilder resultBuilder = new StringBuilder();
        String spaces = new String(new char[indent]).replace("\0", " ");
        resultBuilder.append(String.format("<%s>%s%s\n",
                getName(),
                id > Integer.MIN_VALUE? String.format(" %s", String.valueOf(id)) : "",
                description.isPresent()? String.format(" %s", description.get()) : ""));
        resultBuilder.append(getKeyValuePairs().toString(indent).replaceAll(
                "([^\n]*\n)",
                spaces + "$1"));
        getElements().forEach(
                element -> resultBuilder.append(element.toString(indent).replaceAll(
                        "([^\n]*\n)",
                        spaces + "$1")));
        resultBuilder.append(String.format("</%s>\n", getName()));
        return resultBuilder.toString();
    }

    private String getCloseTag() { return "</" + getName() + ">"; }

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

    @Override
    public void addElement(Element element) {
        if (element == null)
            throw new NodeException("Try to add null as an element to segment.");
        elements.add(element);
        element.setParent(this);
    }

    @Override
    public boolean containsElement(String name) {
        for (Element element : elements)
            if (element.getName().equals(name))
                return true;
        return false;
    }

    @Override
    public List<Element> getElements() {
        return elements;
    }

    @Override
    public List<Element> getElements(String name) {
        return elements.stream()
                .filter(element -> element.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public Element getFirstElement(String name) {
        List<Element> result = getElements(name);
        return result.size() > 0? result.get(0) : null;
    }

    @Override
    public Optional<Element> getFirstElementOptional(String name) {
        List<Element> result = getElements(name);
        return result.size() > 0? Optional.of(result.get(0)) : Optional.empty();
    }
}
