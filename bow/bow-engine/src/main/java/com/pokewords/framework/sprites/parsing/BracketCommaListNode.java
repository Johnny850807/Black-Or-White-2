package com.pokewords.framework.sprites.parsing;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nyngwang
 */
public class BracketCommaListNode extends ListNode {
    private List<String> list = new ArrayList<>();

    public BracketCommaListNode() {}

    public BracketCommaListNode(String name) {
        super(name);
    }

    @Override
    public void parse(Context context) {
        String atName = context.fetchNextToken(
                "@\\S+", "Invalid list node name: " + context.peekToken());
        setName(atName.replaceAll("@(\\S+)", "$1"));

        String leftBracket = context.fetchNextToken(
                "\\[", "Expect an open bracket [ but get: " + context.peekToken());

        int NthElementToAdd = 0;

        while (!context.peekToken().matches("]")) {
            NthElementToAdd++;
            if (NthElementToAdd > 1)
                context.fetchNextToken(
                        ",", "Expect a comma before list element" + context.peekToken());
            String element = context.fetchNextToken(
                    "[^,\\s]+", "Invalid list content " + context.peekToken());
            add(element);
        }

        String rightBracket = context.fetchNextToken(
                "]", "Expect an close bracket ] but get: " + context.peekToken());
    }

    @Override
    public String toString(int contentIndent) {
        StringBuilder resultBuilder = new StringBuilder();
        String spaces = new String(new char[contentIndent]).replace("\0", " ");
        resultBuilder.append(String.format("@%s [", getName()));
        if (list.size() == 0) {
            resultBuilder.append("]\n");
            return resultBuilder.toString();
        }
        resultBuilder.append("\n");
        int NthElement = 0;
        for (String element : list) {
            int widthCounter = NthElement % 5 + 1;
            NthElement++;
            if (widthCounter == 1 && NthElement > 1) {
                resultBuilder
                        .append(",\n")
                        .append(spaces).append(element);
                continue;
            }
            if (NthElement == 1)
                resultBuilder.append(spaces);
            if (widthCounter > 1)
                resultBuilder.append(", ");
            resultBuilder.append(element);
        }
        resultBuilder.append("\n]\n");
        return resultBuilder.toString();
    }

    @Override
    public int getInt(int index) {
        return 0;
    }

    @Override
    public String getString(int index) {
        return null;
    }

    @Override
    public List<String> getList() {
        return list;
    }

    @Override
    public void add(Object object) {
        list.add(String.valueOf(object));
    }

    @Override
    public void remove(Object object) {
        list.remove(String.valueOf(object));
    }

    @Override
    public void clear() {
        list.clear();
    }

    public static void main(String[] args) {
        String sample =
                "@enumSpace [\n" +
                        "    5, 12, 34, apple, orange, bubble, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10\n" +
                        "]";
        ListNode listNode = new BracketCommaListNode();
        listNode.parse(Context.fromText(sample));
        System.out.println(listNode.toString(4));
    }
}
