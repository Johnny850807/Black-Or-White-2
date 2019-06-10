package com.pokewords.framework.sprites.parsing;


/**
 * @author nyngwang
 */
public class MetaSegment extends AngularSegment {
    private ListNode include = new BracketCommaListNode();
    private Element parameters = new AngularElement();

    public MetaSegment() {
        setName("<meta>");
    }

    @Override
    public void parse(Context context) {
        String openTag = context.fetchNextToken(
                "<meta>", "Invalid meta open tag: " + context.peekToken());
        include.parse(context);
        parameters.parse(context);
        String closeTag = context.fetchNextToken(
                "</meta>", "Invalid meta close tag: " + context.peekToken());
    }
}
