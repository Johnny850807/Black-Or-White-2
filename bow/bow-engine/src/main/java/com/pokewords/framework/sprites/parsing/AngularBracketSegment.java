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
        if (!context.hasNextToken())
            return;
        String openTag = context.fetchNextToken();
        if (!openTag.matches("<[^/\\s]\\S+>"))
            throw new ScriptParsingException(String.format(
                    "Should be a <openTag> but receive: %s", openTag));
        setName(deTag(openTag));
        if (!context.hasNextToken())
            throw new ScriptParsingException(
                    String.format("The id of %s is missing", openTag));
        String id = context.fetchNextToken();
        if (!id.matches("0|[1-9]\\d*"))
            throw new ScriptParsingException(String.format(
                    "The id should be integer format: <%s> %s", getName(), id));
        keyValuePairs = new NoCommaPairs(this);
        elements = new ArrayList<>();
        description = Optional.empty();
        String mayBeCloseTag = context.fetchNextToken();
        if (tagsMatch(closeTag, mayBeCloseTag))
            return;

        String mayBeKey = mayBeCloseTag;

        String first = context.fetchNextToken();
        if (context.peekToken().matches("</\\S+>"))
    }

    @Override
    public String toString(int indent) {
        return null;
    }

    private String deTag(String tag) {
        return tag.replaceAll("</?(\\S+)>", "$1");
    }

    private boolean tagsMatch(String closeTag, String mayBeCloseTag) {
        if (mayBeCloseTag.matches("</\\S+>")) {
            if (mayBeCloseTag.equals(closeTag))
                return;
            throw new ScriptParsingException(String.format(
                    "Angular brackets do not match: %s %s", openTag, mayBeCloseTag));
        }
    }
}
