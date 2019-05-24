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
        if (!context.peekToken().matches("<[^/\\s]\\S+>"))
            return;
        String openTag = context.fetchNextToken();
        setName(deTag(openTag));
        String id = context.hasNextToken()?
                context.fetchNextToken(
                        "0|[1-9]\\d*",
                        "Invalid id: " + context.peekToken())
                : context.fetchNextToken("Run out of token before reaching: id");
        this.id = Integer.parseInt(id);
        String at1AfterId = context.fetchNextToken("Run out of token before reaching: </" + getName() + ">");
        if (at1AfterId.equals("</" + getName() + ">")) {
            description = Optional.empty();
            return;
        }
        String at2AfterId = context.fetchNextToken("Run out of token before reaching: </" + getName() + ">");
        if (at2AfterId.equals("</" + getName() + ">")) {
            description = Optional.of(at1AfterId);
            return;
        }
        if (at2AfterId.matches("[^\\s:<>]+")) { // it's key
            description = Optional.of(at1AfterId);
            context.putBack(at2AfterId);
        }
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
