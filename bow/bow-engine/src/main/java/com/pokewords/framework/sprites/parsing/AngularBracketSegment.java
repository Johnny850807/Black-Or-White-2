package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.ScriptParsingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AngularBracketSegment extends Segment {
    private int id;
    private Optional<String> description;

    public AngularBracketSegment(Node parent, String name, KeyValuePairs keyValuePairs, List<Element> elements,
                                 int id, String description) {
        super(parent, name, keyValuePairs, elements);
        this.id = id;
        this.description = Optional.ofNullable(description);
    }

    public AngularBracketSegment(String name, int id, String description) {
        this(null, name, null, new ArrayList<>(), id, description);
        keyValuePairs = new NoCommaPairs(this);
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
                "<[^/\\s]\\S+>",
                "Invalid <openTag>: " + context.peekToken());
        setName(deTag(openTag));
        String id = context.hasNextToken()?
                context.fetchNextToken(
                        "0|[1-9]\\d*",
                        "")
                : context.fetchNextToken("Run out of token before reaching: id");
        String mayBeDescription = context.hasNextToken()?
                context.fetchNextToken(
                        "\\S+",
                        "Invalid description: " + context.peekToken())
                : context.fetchNextToken("Run out of token after reaching: <"+getName()+"> "+id);
        description = context.peekToken().matches(":")? Optional.empty() : Optional.of(mayBeDescription);
        if (!description.isPresent())
            context.putBack(mayBeDescription);
        do {
            if (context.hasNextToken())
                keyValuePairs.parse(context);
            if (context.hasNextToken()) {
                Element element = new AngularBracketElement();
                element.parse(context);
                context;
            }
            String mayBeCloseTag = context.fetchNextToken(
                    "Run out of token before reaching: </"+getName()+">");
            if (mayBeCloseTag.equals("</"+getName()+">"))
                return;
            context.putBack(mayBeCloseTag);
        } while (true);
    }

    @Override
    public String toString(int indent) {
        return null;
    }

    private String deTag(String tag) {
        return tag.replaceAll("</?(\\S+)>", "$1");
    }
}
