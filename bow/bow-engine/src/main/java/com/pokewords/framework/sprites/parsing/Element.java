package com.pokewords.framework.sprites.parsing;

import java.util.Collection;
import java.util.Optional;
import java.util.OptionalInt;


/**
 * An Element doesn't contain other Nodes.
 * @author nyngwang
 */
public abstract class Element implements Node {
    private KeyValuePairs pairs = new NoCommaPairs();

    public static String deTag(String tag) {
        return tag.replaceAll("</?(\\S+)>", "$1");
    }

    boolean parseTag(Context context) {
        if (context.getTag() == null)
            return false;
        name = Context.deTag(context.getTag());
        return true;
    }

    boolean parseClosedTag(Context context) {
        if (context.getTag() == null)
            return false;
        return name.equals(Context.deTag(context.getTag()));
    }
}
