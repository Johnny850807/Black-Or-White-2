package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.ScriptParsingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class LinScriptGenerator extends Script {
    public static Script fromShortHand(String path) {
        LinScriptGenerator generator = new LinScriptGenerator();
        generator.parse(Context.fromFile(path));
        return generator.getScript();
    }

    private Script script;
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

    private Script getScript() {
        return script;
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
                    Element element = new AngularBracketElement();
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
        Script LinScript = LinScriptGenerator.fromShortHand(
                "bow-engine/src/main/resources/assets/scripts/templateTest.bow");
        System.out.println(LinScript);
    }
}
