package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.ScriptParsingErrorException;
import com.pokewords.framework.engine.exceptions.SegmentNameUnrecognizableException;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.components.Frame;
import com.pokewords.framework.sprites.components.FrameFactory;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *  Is it possible to pass only Script to FrameFactory?
 *  TODO: Clear Exception Message.
 *
 *  @author nyngwang
 */
public class LinScriptTextParser implements ScriptTextParser {

    private FrameFactory frameFactory;
    private FrameStateMachineComponent fsmc;
    private FrameSegment frameSegment;
    private GallerySegment gallerySegment;
    private Script script;
    private OnParsingFrameListener listener;

    /**
     * Constructor.
     * @param iocFactory abstract factory for dependency.
     */
    public LinScriptTextParser(IocFactory iocFactory) {
        this.frameFactory = iocFactory.frameFactory();
        fsmc = null;
        frameSegment = null;
        gallerySegment = null;
        script = null;
        listener = null;
    }

    private void init() {
        fsmc = null;
        frameSegment = null;
        gallerySegment = null;
        script = null;
        listener = null;
    }

    @Override
    public FrameStateMachineComponent parse(Script script, OnParsingFrameListener listener) {

        // Prepare for fsm construction.
        fsmc = new FrameStateMachineComponent();
        this.script = script;
        this.listener = listener;

        // Start parsing.
        String scriptText = script.getText();
        Pattern globalTagPattern = Pattern.compile(
                "<(\\w+)>(.*?)</\\1>",
                Pattern.DOTALL | Pattern.MULTILINE);
        Matcher globalTagPatternMatcher = globalTagPattern.matcher(scriptText);

        while (globalTagPatternMatcher.find()) {

            String tagName = globalTagPatternMatcher.group(1);
            String tagContent = globalTagPatternMatcher.group(2);
            processTag(tagName, tagContent);
        }

        init();

        return fsmc;
    }


    /**
     * Process the tag.
     * @param tagName name of the tag to be parsed.
     * @param tagContent content of the tag to be parsed.
     */
    private void processTag(String tagName, String tagContent) {
        switch (tagName) {
            case "gallery": processGalleryTag(tagContent); return;
            case "frame": processFrameTag(tagContent); return;
            default: throw new SegmentNameUnrecognizableException("Segment name is unrecognizable!");
        }
    }


    /**
     * Process the gallery tag.
     * @param tagContent content of the tag to be parsed.
     */
    private void processGalleryTag(String tagContent) {

        // Parse key-value pairs in the gallery tag.
        Pattern pattern1 = Pattern.compile("(\\w+)\\s*:\\s*(\\S+)");
        Pattern pattern2 = Pattern.compile("(\\w+)\\s*:\\s*([+-]?\\d+)");

        Matcher matcher1 = pattern1.matcher(tagContent);
        while (matcher1.find()) {
            String key = matcher1.group(1);
            String value = matcher1.group(2);
            gallerySegment.addPair(key, value);
        }

        Matcher matcher2 = pattern2.matcher(tagContent);
        while (matcher2.find()) {
            String key = matcher2.group(1);
            int value = Integer.parseInt(matcher2.group(2));
            gallerySegment.addPair(key, value);
        }

        script.addGallerySegment(gallerySegment);
    }

    private void processElementTag(String tagName, String tagContent) {

        Element element = new Element();
        Pattern pattern1 = Pattern.compile("(\\w+)\\s*:\\s*(\\S+)");
        Pattern pattern2 = Pattern.compile("(\\w+)\\s*:\\s*([+-]?\\d+)");

        Matcher matcher1 = pattern1.matcher(tagContent);
        while (matcher1.find()) {
            String key = matcher1.group(1);
            String value = matcher1.group(2);
            element.addPair(key, value);
        }

        Matcher matcher2 = pattern2.matcher(tagContent);
        while (matcher2.find()) {
            String key = matcher2.group(1);
            int value = Integer.parseInt(matcher2.group(2));
            element.addPair(key, value);
        }

        frameSegment.addElement(tagName, element);
    }

    private void processFrameTag(String tagContent) {

        // [!] Under construction.

        // 1. Find frameNumber, frameName
        Pattern pattern1 = Pattern.compile("<frame>\\s*(\\d+)\\s+(\\w+)");
        Matcher pattern1Matcher = pattern1.matcher(tagContent);

        if (pattern1Matcher.find()) {

            int frameNumber = Integer.parseInt(pattern1Matcher.group(1));
            String frameName = pattern1Matcher.group(2);
            frameSegment = new FrameSegment(script, frameNumber, frameName);
            script.addFrameSegment(frameSegment);

        } else throw new ScriptParsingErrorException(
                "Frame node: frameNumber, frameName format is incorrect!"
        );

        // 2. Find attribute pairs
        Pattern pattern2 = Pattern.compile(
                "\\n(.*?)<(?!/)",
                Pattern.DOTALL | Pattern.MULTILINE);
        Pattern pattern2_1 = Pattern.compile("(\\w+)\\s*:\\s*(\\S+)");
        Pattern pattern2_2 = Pattern.compile("(\\w+)\\s*:\\s*([+-]?\\d+)");
        Matcher pattern2Matcher = pattern2.matcher(tagContent);

        if (!pattern2Matcher.find()) {
            throw new ScriptParsingErrorException(
                    "Frame node: attributes pairs are missing!");
        } else do {

            String keyValuePairs = pattern2Matcher.group(1);
            Matcher pattern2_1Matcher = pattern2_1.matcher(keyValuePairs);
            while (pattern2_1Matcher.find()) {
                String key = pattern2_1Matcher.group(1);
                String value = pattern2_1Matcher.group(2);
                frameSegment.addPair(key, value);
            }

            Matcher pattern2_2Matcher = pattern2_2.matcher(keyValuePairs);
            while (pattern2_2Matcher.find()) {
                String key = pattern2_2Matcher.group(1);
                int value = Integer.parseInt(pattern2_2Matcher.group(2));
                frameSegment.addPair(key, value);
            }

        } while (pattern2Matcher.find());


        // 3. Find Element tags
        Pattern pattern3 = Pattern.compile(
                "<(\\w+)>(.*?)</\\1>",
                Pattern.DOTALL | Pattern.MULTILINE);
        Matcher pattern3Matcher = pattern3.matcher(tagContent);
        while (pattern3Matcher.find()) {
            String elementName = pattern3Matcher.group(1);
            String elementContent = pattern3Matcher.group(2);
            processElementTag(elementName, elementContent);
        }

        // 4. Before creating frame, pass it frameSegment to client's rule
        listener.onParsing(frameSegment);


        // 5. Finally, create frame and add it to fsmc
        Frame frame = frameFactory.createFrame(frameSegment);
//        fsmc.addState(frame);
    }

}
