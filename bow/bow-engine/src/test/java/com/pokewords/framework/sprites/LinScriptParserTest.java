package com.pokewords.framework.sprites;

import com.pokewords.framework.commons.utils.Resources;
import com.pokewords.framework.sprites.parsing.*;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author shawn
 */
public class LinScriptParserTest {
    @Test
    public void testParserOnlyFrame(){
        Script script = new LinScript();
        script.parse(Context.fromFile(Resources.get("script/TestOnlyFrameScript.bow")));

        String transitionsName = "transitions";
        String bodyName = "body";
        String effectName = "effect";

        //First frame
        int firstFrameDuration = 80;
        int firstFrameNext = 1;
        int firstFramePic = 0;
        int firstFrameLayer = 2;

        Segment firstFrameSegment = script.getSegments().get(0);

        assertEquals(script.getFirstSegment("frame").getId(), 0);
        Map<String, String> firstFramePairs = firstFrameSegment.getMap();
        assertEquals(String.valueOf(firstFrameDuration), firstFramePairs.get("duration"));
        assertEquals(String.valueOf(firstFrameNext), firstFramePairs.get("next"));
        assertEquals(String.valueOf(firstFramePic), firstFramePairs.get("pic"));
        assertEquals(String.valueOf(firstFrameLayer), firstFramePairs.get("layer"));

        List<Element> firstFrameElements =  script.getSegments().get(0).getElements();
        assertEquals(transitionsName, firstFrameElements.get(0).getName());
        assertEquals(bodyName, firstFrameElements.get(1).getName());
        assertEquals(effectName, firstFrameElements.get(2).getName());

        Element firstFrameTransitionsElement = firstFrameElements.get(0);
        Map<String, String> transitionsPairs = firstFrameTransitionsElement.getMap();
        assertEquals(transitionsPairs.get("classType"), "com.demo.Types");
        assertEquals(transitionsPairs.get("walkLeft"), "0");
        assertEquals(transitionsPairs.get("walkRight"), "3");
        assertEquals(transitionsPairs.get("walkUp"), "6");
        assertEquals(transitionsPairs.get("walkDown"), "9");
        assertEquals(transitionsPairs.get("shoot"), "1500");

        Element firstFrameBodyElement = firstFrameElements.get(1);
        Map<String, String> bodyPairs = firstFrameBodyElement.getMap();
        assertEquals(bodyPairs.get("x"), "16");
        assertEquals(bodyPairs.get("y"), "10");
        assertEquals(bodyPairs.get("w"), "49");
        assertEquals(bodyPairs.get("h"), "67");

        Element firstFrameEffectElement = firstFrameElements.get(2);
        Map<String, String> effectPairs = firstFrameEffectElement.getMap();
        assertEquals(effectPairs.get("moveX"), "-8");


        //Second frame
        int secondFrameDuration = 100;
        int secondFrameNext = 2;
        int secondFramePic = 1;
        int secondFrameLayer = 2;
        Segment secondFrameSegment = script.getSegments().get(1);
        assertEquals(secondFrameSegment.getId(), 1);

        Map<String, String> secondFramePairs = secondFrameSegment.getMap();

        assertEquals(String.valueOf(secondFrameDuration), secondFramePairs.get("duration"));
        assertEquals(String.valueOf(secondFrameNext), secondFramePairs.get("next"));
        assertEquals(String.valueOf(secondFramePic), secondFramePairs.get("pic"));
        assertEquals(String.valueOf(secondFrameLayer), secondFramePairs.get("layer"));

        List<Element> secondFrameElements =  secondFrameSegment.getElements();
        Element secondFrameTransitionsElement = secondFrameElements.get(0);
        Element secondFrameBodyElement = secondFrameElements.get(1);
        Element secondFrameEffectElement = secondFrameElements.get(2);

        assertEquals(transitionsName, secondFrameTransitionsElement.getName());
        assertEquals(bodyName, secondFrameBodyElement.getName());
        assertEquals(effectName, secondFrameEffectElement.getName());



        Map<String, String> secondFrameTransitionsPairs = secondFrameTransitionsElement.getMap();

        assertEquals(secondFrameTransitionsPairs.get("classType"), "com.demo.Types");
        assertEquals(secondFrameTransitionsPairs.get("walkLeft"), "0");
        assertEquals(secondFrameTransitionsPairs.get("walkRight"), "3");
        assertEquals(secondFrameTransitionsPairs.get("walkUp"), "6");
        assertEquals(secondFrameTransitionsPairs.get("walkDown"), "9");
        assertEquals(secondFrameTransitionsPairs.get("run"), "9527");

        Map<String, String> secondFrameBodyPairs = secondFrameBodyElement.getMap();

        assertEquals(secondFrameBodyPairs.get("x"), "16");
        assertEquals(secondFrameBodyPairs.get("y"), "10");
        assertEquals(secondFrameBodyPairs.get("w"), "49");
        assertEquals(secondFrameBodyPairs.get("h"), "67");

        Map<String, String> secondFrameEffectPairs = secondFrameEffectElement.getMap();
        assertEquals(secondFrameEffectPairs.get("moveX"), "-8");
    }


    @Test
    public void testParametersAndGalleriesParser(){
        Script script = new LinScript();
        script.parse(Context.fromFile(Resources.get("script/TestGalleriesScript.bow")));


        Segment parametersSegment = script.getFirstSegment("parameters");
        Map<String, String> parametersPairs = parametersSegment.getMap();
        String speed = "10";
        assertEquals(speed, parametersPairs.get("speed"));

        String galleriesName = "galleries";
        String sheetName = "sheet";
        Segment galleriesSegment = script.getSegments().get(1);
        Element sheetElement = galleriesSegment.getElements().get(0);

        assertEquals(galleriesSegment.getName(), galleriesName);
        assertEquals(sheetElement.getName(), sheetName);

        String sheetStartPic = "0";
        String sheetEndPic = "6";
        String sheetRow = "3";
        String sheetCol = "3";
        String sheetPath = "sheets/character.png";
        Map<String, String> sheetPairs =  sheetElement.getMap();
        assertEquals(sheetPairs.get("startPic"), sheetStartPic);
        assertEquals(sheetPairs.get("endPic"), sheetEndPic);
        assertEquals(sheetPairs.get("row"), sheetRow);
        assertEquals(sheetPairs.get("col"), sheetCol);
        assertEquals(sheetPairs.get("path"), sheetPath);
    }

    @Test
    public void testGetSegmentsAndElementsThroughName(){
        Script script = new LinScript();
        script.parse(Context.fromFile(Resources.get("script/TestChaosScript.bow")));

        List<Segment> frameSegments = script.getSegments("frame");

        int firstFrameId = 0;
        int secondFrameId = 1;
        Segment firstSegment = script.getFirstSegment("frame");
        assertEquals(firstSegment.getId(), secondFrameId);
        assertEquals(frameSegments.get(1).getId(), firstFrameId);

        Element getFirstElementByIndex = firstSegment.getElements("transitions").get(0);
        Element getFirstElementByFunction =firstSegment.getFirstElement("transitions");
        assertEquals(getFirstElementByIndex, getFirstElementByFunction);


        List<Segment> galleriesSegments = script.getSegments("galleries");
        Segment onlyOneGalleriesSegment = galleriesSegments.get(0);
        List<Element> onlyOneSheetElement = onlyOneGalleriesSegment.getElements("sheet");
        Element sheetElement = onlyOneSheetElement.get(0);
        String sheetStartPic = "0";
        String sheetEndPic = "6";
        String sheetRow = "3";
        String sheetCol = "3";
        String sheetPath = "sheets/character.png";
        Map<String, String> sheetPairs =  sheetElement.getMap();
        assertEquals(sheetPairs.get("startPic"), sheetStartPic);
        assertEquals(sheetPairs.get("endPic"), sheetEndPic);
        assertEquals(sheetPairs.get("row"), sheetRow);
        assertEquals(sheetPairs.get("col"), sheetCol);
        assertEquals(sheetPairs.get("path"), sheetPath);

    }

}
