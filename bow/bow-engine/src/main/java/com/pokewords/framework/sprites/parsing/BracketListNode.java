package com.pokewords.framework.sprites.parsing;

public class BracketListNode extends ListNode {
    public BracketListNode() {}

    public BracketListNode(String name) {
        super(name);
    }

    @Override
    public void parse(Context context) {
        String atName = context.fetchNextToken(
                "@\\S+", "Invalid list node name: " + context.peekToken());
        setName(atName.replaceAll("@(\\S+)", "$1"));

        String leftBracket = context.fetchNextToken(
                "\\[", "Expect an open bracket [ but get: " + context.peekToken());

        int elementCount = 0;

        while (!context.peekToken().matches("]")) {
            elementCount++;
            if (elementCount > 1)
                context.fetchNextToken(
                        ",", "Expect a comma before list element" + context.peekToken());
            String element = context.fetchNextToken(
                    "[^,\\s]+", "Invalid list element " + context.peekToken());
            add(element);
        }

        String rightBracket = context.fetchNextToken(
                "]", "Expect an close bracket ] but get: " + context.peekToken());
    }

    @Override
    public String toString(int indent) {
        StringBuilder resultBuilder = new StringBuilder();
        String spaces = new String(new char[indent]).replace("\0", " ");
        resultBuilder.append(String.format("@%s [", getName()));
        if (getList().size() == 0) {
            resultBuilder.append("]\n");
            return resultBuilder.toString();
        }
        resultBuilder.append("\n");
        int counter = 0;
        for (String element : getList()) {
            int widthCounter = counter % 5 + 1;
            counter++;
            if (widthCounter == 1)
                resultBuilder.append(spaces);
            if (counter > 1)
                resultBuilder.append(", ");
            resultBuilder.append(element);
            if (widthCounter == 5)
                resultBuilder.append("\n");
        }
        resultBuilder.append("\n]\n");
        return resultBuilder.toString();
    }

    public static void main(String[] args) {
        String sample =
                "@enumSpace [\n" +
                "    5, 12, 34, apple\n" +
                "]";
        ListNode listNode = new BracketListNode();
        listNode.parse(Context.fromText(sample));
        System.out.println(listNode.toString(4));
    }
}
