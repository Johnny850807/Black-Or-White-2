package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.bundles.ReadOnlyBundle;
import com.pokewords.framework.engine.exceptions.NodeException;
import com.pokewords.framework.engine.exceptions.ScriptParsingException;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author nyngwang
 */
public class AngularSegment extends Segment {
    private List<Element> elements = new ArrayList<>();
    private List<ListNode> listNodes = new ArrayList<>();

    public AngularSegment() {}

    public AngularSegment(String name, int id, @Nullable String description) {
        super(name, id, description);
    }

    public AngularSegment(String name, int id) {
        this(name, id, null);
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
        if (at1AfterId.matches("<[^/\\s]\\S+>")
                || at2AfterId.matches(":") || at2AfterId.matches("\\[")) {
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
            if (context.peekToken().matches("@\\S+")) { // It's listNode
                ListNode listNode = new BracketCommaListNode();
                listNode.parse(context);
                addListNode(listNode);
                continue;
            }
            if (context.peekToken().matches("[^\\s:<>\\[\\]]+")) { //It's key
                do {
                    String key = context.fetchNextToken(
                            "[^\\s:<>\\[\\]]+", "Invalid key format: " + context.peekToken());
                    String colon = context.fetchNextToken(
                            ":", "Expect: " + key + ": " + context.peekToken());
                    String value = context.fetchNextToken("[^\\s:<>]+", "Invalid value: " + context.peekToken());
                    put(key, value);
                } while (context.peekToken().matches("[^\\s:<>\\[\\]]+"));
                continue;
            }
            if (context.peekToken().matches("<[^/\\s]\\S+>")) { // It's element
                Element element = new AngularElement();
                element.parse(context);
                addElement(element);
                continue;
            }
            throw new ScriptParsingException(
                    "Cannot recognize symbol in segment body: " + context.peekToken());
        } while (true);
    }

    @Override
    public String toString(int contentIndent) {
        StringBuilder resultBuilder = new StringBuilder();
        String spaces = new String(new char[contentIndent]).replace("\0", " ");
        resultBuilder.append(String.format("<%s> %s%s\n",
                getName(),
                getId() > Integer.MIN_VALUE? getId() : "undefined",
                getDescription().isPresent()? " " + getDescription().get() : ""));

        int counter = 0;
        int width = 5;
        for (Map.Entry entry: getMap().entrySet()) {
            counter = counter % width + 1;
            resultBuilder.append(spaces).append(
                    String.format("%s%s: %s%s",
                            counter > 1? " ": "",
                            entry.getKey(), entry.getValue(),
                            counter == width? "\n": ""));
        }
        if (counter > 0 && counter != width)
            resultBuilder.append('\n');

        listNodes.forEach(listNode ->
                resultBuilder.append(listNode.toString(contentIndent).replaceAll(
                        "([^\n]*\n)",
                        spaces + "$1")));
        elements.forEach(element ->
                resultBuilder.append(element.toString(contentIndent).replaceAll(
                        "([^\n]*\n)",
                        spaces + "$1")));
        resultBuilder.append(String.format("</%s>\n", getName()));
        return resultBuilder.toString();
    }

    private String getCloseTag() { return "</" + getName() + ">"; }

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

    @Override
    public void addListNode(ListNode listNode) {
        if (listNode == null)
            throw new NodeException("Cannot add null list node.");
        if (containsListNode(listNode.getName()))
            throw new NodeException("List node name duplicated: " + listNode.getName());
        listNodes.add(listNode);
        listNode.setParent(this);
    }

    @Override
    public boolean containsListNode(String name) {
        for (ListNode listNode : listNodes)
            if (listNode.getName().equals(name))
                return true;
        return false;
    }

    @Override
    public ListNode getListNode(String name) {
        for (ListNode listNode : listNodes)
            if (listNode.getName().equals(name))
                return listNode;
        return null;
    }

    @Override
    public Optional<ListNode> getListNodeOptional(String name) {
        ListNode result = getListNode(name);
        return result == null? Optional.empty() : Optional.of(result);
    }
}