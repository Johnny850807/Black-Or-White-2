package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.ScriptParsingException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class AngularBracketSegment extends Segment {
    public AngularBracketSegment(Node parent, String name, @NotNull KeyValuePairs keyValuePairs,
                                 int id, String description, List<Element> elements) {
        super(parent, name, keyValuePairs, id, description, elements);
    }

    public AngularBracketSegment(String name, int id, String description) {
        this(null, name, new NoCommaPairs(), id, description, new ArrayList<>());
    }

    public AngularBracketSegment(String name, int id) {
        this(name, id, null);
    }


    public AngularBracketSegment() {
        this(null, Integer.MIN_VALUE);
    }

    @Override
    public void parse(Context context) {
        String openTag = context.fetchNextToken(
                "<[^/\\s]\\S+>", "Invalid open tag: " + context.peekToken());
        setName(deTag(openTag));

        String id = context.fetchNextToken("0|[1-9]\\d*", "Invalid id: " + context.peekToken());
        this.id = Integer.parseInt(id);

        String at1AfterId = context.fetchNextToken();
        if (at1AfterId.equals(getCloseTag())) {
            description = Optional.empty();
            return;
        }

        String at2AfterId = context.fetchNextToken();
        if (at2AfterId.equals(getCloseTag())) {
            description = Optional.of(at1AfterId);
            return;
        }

        if (at1AfterId.matches("<[^/\\s]\\S+>") || at2AfterId.matches(":")) {
            description = Optional.empty();
            context.putBack(at2AfterId);
            context.putBack(at1AfterId);
        } else { // has description
            description = Optional.of(at1AfterId);
            context.putBack(at2AfterId);
        }

        do {
            if (context.peekToken().equals(getCloseTag())) {
                context.consumeOneToken();
                return;
            }
            if (context.peekToken().matches("<[^/\\s]\\S+>")) { // It's element
                Element element = new AngularBracketElement();
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

    private String deTag(String tag) {
        return tag.replaceAll("</?(\\S+)>", "$1");
    }

    private String getCloseTag() { return "</" + getName() + ">"; }
}
