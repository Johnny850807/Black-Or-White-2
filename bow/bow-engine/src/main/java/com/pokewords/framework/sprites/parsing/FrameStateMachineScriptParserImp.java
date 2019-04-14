package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.SegmentNameUnrecognizableException;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.components.Frame;
import com.pokewords.framework.sprites.components.FrameFactory;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FrameStateMachineScriptParserImp implements FrameStateMachineScriptParser {

    private FrameFactory frameFactory;
    private FrameStateMachineComponent fsmc;
    private Script script;
    private OnParsingFrameListener listener;

    public FrameStateMachineScriptParserImp(IocFactory iocFactory) {
        this.frameFactory = iocFactory.frameFactory();
        fsmc = null;
        script = null;
    }


    @Override
    public FrameStateMachineComponent parse(Script script, OnParsingFrameListener listener) {

        // Initialize all fsm related objects.
        fsmc = new FrameStateMachineComponent();
        this.script = script;
        this.listener = listener;

        String scriptTxt = script.getText();
        Pattern segmentPattern = Pattern.compile("<(\\w+)>(.*?)</\\1>",
                                                 Pattern.DOTALL | Pattern.MULTILINE);
        Matcher matcher = segmentPattern.matcher(scriptTxt);

        while (matcher.find()) {
            String segmentName = matcher.group(1);
            String segmentContent = matcher.group(2);
            processSegment(segmentName, segmentContent);
        }

        return null;
    }


    private void processSegment(String segmentName, String segmentContent) {

        switch (segmentName) {
            case "profile": processGallerySegment(segmentContent); return;
            case "frame": processFrameSegment(segmentContent); return;
            default:
                throw new SegmentNameUnrecognizableException("Segment name is unrecognizable!");
        }
    }


    /**
     *
     * @param segmentContent
     */
    private void processGallerySegment(String segmentContent) {

        // Parse key-value pairs in the GallerySegment.
        segmentContent = segmentContent.trim();
        String[] lines = segmentContent.split("\\s*\n\\s*");
        Pattern keyValuePattern = Pattern.compile("(\\S+):\\s*(\\S+)");
        HashMap<String, String> kvMap = new HashMap<>(); // temporary storage for k-v pairs

        /**
         * For each <profile></profile> tag, it's possible to have two line of
         *     "gallery(xx-xx): path_of_bmp k1:v1 k2:v2...",
         *     but each GallerySegment only have one "path" attribute?
         */
        for (String line : lines) {

            Matcher matcher = keyValuePattern.matcher(line);
            while (matcher.find()) {
                kvMap.put(matcher.group(1), matcher.group(2));
            }

            // Parse key-value pairs needed for the segment.
            int frameWidth = Integer.parseInt(kvMap.get("w"));
            int frameHeight = Integer.parseInt(kvMap.get("h"));
            int row = Integer.parseInt(kvMap.get("row"));
            int column = Integer.parseInt(kvMap.get("col"));
            int startPic = -1; // Let addGallerySegment throw exception.
            int endPic = -1;
            String path = ""; // Let GallerySegment() throw exception.

            Pattern picNumPattern = Pattern.compile("gallery\\((\\d+)-(\\d+)\\)");
            for (String key : kvMap.keySet()) {
                Matcher m = picNumPattern.matcher(key);
                if (m.find()) {
                    startPic = Integer.parseInt(m.group(1));
                    endPic = Integer.parseInt(m.group(2));
                    path = kvMap.get(key);
                    break;
                }
            }

            script.addGallerySegment(startPic, endPic,
                    new GallerySegment(path, frameWidth, frameHeight, row, column));

            kvMap.clear();
        }
    }


    private void processFrameSegment(String segmentContent) {

        // [!] Under construction.

        // Parse key-value pairs needed for the segment.
        segmentContent = segmentContent.trim();
        String[] lines = segmentContent.split("\\s*\n\\s*");
        Pattern keyValuePattern = Pattern.compile("(\\S+):\\s*(\\S+)");

        int frameNumber = 0;
        String frameName = "";
        FrameSegment frameSegment = new FrameSegment(script, frameNumber, frameName);

        frameSegment.addPair("", 1);
        frameSegment.addPair("", "");
        Map<String, String> stringMap = new HashMap<>();
        Map<String, Integer> intMap = new HashMap<>();


        frameSegment.addElement("", new Element(stringMap, intMap));


        Frame frame = frameFactory.createFrame(frameSegment);


        // Add to fsmc
        fsmc.addState(frame);
    }


    // 用FrameFactory建立FrameNode
    // 改傳FrameSegment給Facotry
    // FrameSegment
    // 清楚的ParsingException
}
