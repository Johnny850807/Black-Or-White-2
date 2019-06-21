package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.ScriptParsingException;

import java.util.*;
import java.util.regex.Pattern;

/**
 * TODO: Should check it again.
 * @author nyngwang
 */
public class LinScriptGenerator extends Script {
    public static LinScript fromPath(String path) {
        LinScriptGenerator generator = new LinScriptGenerator();
        generator.parse(Context.fromPath(path));
        return generator.getScript();
    }

    private LinScript script;
    private Map<String, KeyValuePairs> nameToForAllPairs;
    private Map<String, Map<Integer, KeyValuePairs>> nameToTargetPairs;
    private Map<String, List<Element>> nameToForAllElements;
    private Map<String, Map<Integer, List<Element>>> nameToTargetElements;

    private LinScriptGenerator() {
        nameToForAllPairs = new HashMap<>();
        nameToTargetPairs = new HashMap<>();
        nameToForAllElements = new HashMap<>();
        nameToTargetElements = new HashMap<>();
        script = new LinScript();
    }

    private LinScript getScript() {
        return script;
    }

    @Override
    public void setParent(Node parent) {
        script.setParent(parent);
    }

    @Override
    public Node getParent() {
        return script.getParent();
    }

    @Override
    public void parse(Context context) {
        do {
            context.fetchNextToken(
                    "\\[]",
                    "Expect [] but received: " + context.peekToken());
            String openTag = context.fetchNextToken(
                    "<[^/\\s]\\S+>",
                    "Invalid open tag: " + context.peekToken());
            String name = deTag(openTag, "<", ">");
            String closeTag = getCloseTag(name);
            nameToTargetPairs.put(name, new HashMap<>());
            nameToForAllElements.put(name, new ArrayList<>());
            nameToTargetElements.put(name, new HashMap<>());

            do {
                String repeat = deTag(context.fetchNextToken(
                        "\\[(?:\\*|[\\d\\s]+)]",
                        "Invalid generator notation: " + context.peekToken()),
                        "[", "]");

                if (context.peekToken().matches("[^\\s:<>\\[\\]]+")) { // It's key
                    KeyValuePairs keyValuePairs = new NoCommaPairs();
                    keyValuePairs.parse(context);
                    if (repeat.equals("*"))
                        nameToForAllPairs.put(name, keyValuePairs);
                    else {
                        for (String id : repeat.split("\\s+"))
                            nameToTargetPairs.get(name).put(Integer.parseInt(id), keyValuePairs);
                    }
                } else if (context.peekToken().matches("<[^/\\s]\\S+>")) { // It's element
                    Element element = new AngularElement();
                    element.parse(context);
                    if (repeat.equals("*"))
                        nameToForAllElements.get(name).add(element);
                    else {
                        for (String id : repeat.split("\\s+"))
                            if (nameToTargetElements.get(name).containsKey(Integer.parseInt(id)))
                                nameToTargetElements.get(name).get(Integer.parseInt(id)).add(element);
                            else {
                                List<Element> elements = new ArrayList<>();
                                elements.add(element);
                                nameToTargetElements.get(name).put(Integer.parseInt(id), elements);
                            }
                    }
                } else
                    throw new ScriptParsingException("Invalid token after []: " + context.peekToken());

            } while (context.peekToken().matches("\\[(?:\\*|[\\d\\s]+)]"));

            context.fetchNextToken(
                    closeTag,
                    "Tag " + openTag + " is not correctly closed by: " + context.peekToken());
        } while (context.peekToken().matches("\\[]"));

        script.parse(context);

        // Map<String, KeyValuePairs> nameToForAllPairs;
        nameToForAllPairs.forEach((name, keyValuePairs) -> {
            checkSegmentNameDoesExist(name);
            script.getSegments(name).forEach(segment -> {
                keyValuePairs.forEach(pair -> {
                    if (!segment.getKeyValuePairs().containsKey(pair.getKey()))
                        segment.getKeyValuePairs().put(pair.getKey(), pair.getValue());
                });
            });
        });

        // Map<String, Map<Integer, KeyValuePairs>> nameToTargetPairs;
        nameToTargetPairs.forEach((name, map) -> {
            checkSegmentNameDoesExist(name);
            // Should be rewrite if all id's among different names are unique.
            map.forEach((id, keyValuePairs) -> {
                // Match id
                script.getSegmentsById(id).forEach(segment -> {
                    // Then match name, since different names can have the same id
                    if (segment.getName().equals(name))
                        keyValuePairs.forEach(pair -> {
                            if (!segment.getKeyValuePairs().containsKey(pair.getKey()))
                                segment.getKeyValuePairs().put(pair.getKey(), pair.getValue());
                        });
                });
            });
        });

        // Map<String, List<Element>> nameToForAllElements;
        nameToForAllElements.forEach((name, elements) -> {
            checkSegmentNameDoesExist(name);
            // TODO: If need to modify script after script built, change to deep copy version
            // If no modification after script built, no need to do deep copy
            script.getSegments(name).forEach(segment -> {
                elements.forEach(segment::addElement);
            });
        });

        // Map<String, Map<Integer, List<Element>>> nameToTargetElements;
        nameToTargetElements.forEach((name, map) -> {
            checkSegmentNameDoesExist(name);
            map.forEach((id, elements) -> {
                script.getSegmentsById(id).forEach(segment -> {
                    if (segment.getName().equals(name))
                        elements.forEach(segment::addElement);
                });
            });
        });
    }

    private void checkSegmentNameDoesExist(String generatorName) {
        if (!script.containsSegment(generatorName))
            throw new ScriptParsingException(String.format(
                    "No target for generator [] <%s>", generatorName));
    }

    @Override
    public String toString(int indent) {
        return script.toString(indent);
    }

    private String deTag(String tag, String leftToken, String rightToken) {
        return tag.replaceAll(Pattern.quote(leftToken) + "/?(.+?)" + Pattern.quote(rightToken), "$1");
    }

    private String getCloseTag(String tagName) {
        return "</" + tagName + ">";
    }

    public static void main(String[] args) {
        Script LinScript = LinScriptGenerator.fromPath(
                "bow-engine/src/main/resources/assets/scripts/templateTest.bow");
        System.out.println(LinScript);
    }

    @Override
    public void addListNode(ListNode listNode) {

    }

    @Override
    public List<ListNode> getListNodes() {
        return null;
    }

    @Override
    public boolean containsListNode(String name) {
        return false;
    }

    @Override
    public List<ListNode> getListNodes(String name) {
        return null;
    }

    @Override
    public ListNode getFirstListNode(String name) {
        return null;
    }

    @Override
    public Optional<ListNode> getFirstListNodeOptional(String name) {
        return Optional.empty();
    }

    @Override
    public void addSegment(Segment segment) {

    }

    @Override
    public List<Segment> getSegments() {
        return null;
    }

    @Override
    public boolean containsSegment(String name) {
        return false;
    }

    @Override
    public boolean containsSegmentId(int id) {
        return false;
    }

    @Override
    public boolean containsSegmentDescription(String description) {
        return false;
    }

    @Override
    public List<Segment> getSegments(String name) {
        return null;
    }

    @Override
    public List<Segment> getSegmentsById(int id) {
        return null;
    }

    @Override
    public List<Segment> getSegmentsByDescription(String description) {
        return null;
    }

    @Override
    public Segment getFirstSegment(String name) {
        return null;
    }

    @Override
    public Optional<Segment> getFirstSegmentOptional(String name) {
        return Optional.empty();
    }
}
