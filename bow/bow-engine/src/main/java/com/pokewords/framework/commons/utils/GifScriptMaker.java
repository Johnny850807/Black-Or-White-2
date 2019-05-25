package com.pokewords.framework.commons.utils;

import com.pokewords.framework.commons.Range;
import com.pokewords.framework.sprites.parsing.*;

/**
 * A utility to fromGallery the script that animates a GIF frame state machine component.
 * The picture and frame number should be sequential and the first GIF picture should have frame event 0
 * @author johnny850807 (waterball)
 */
public class GifScriptMaker {

    public static Script createSequenceScript(String galleryPath,  Range galleryPicRange,
                                              int gifStartPic, int gifEndPic, int duration, int layer) {
        Script script = new LinScript();
        Segment galleriesSegment = new AngularBracketSegment("galleries", 0);
        Element sequenceElement = new AngularBracketElement("sequence");
        galleriesSegment.addElement(sequenceElement);
        script.addSegment(galleriesSegment);
        return makeSequentialFrames(galleryPath, galleryPicRange, gifStartPic, gifEndPic, duration, layer, script, sequenceElement);
    }

    public static Script createSheetScript(String galleryPath, int sheetRow, int sheetCol,
                                           int gifStartPic, int gifEndPic, int duration, int layer) {
        Script script = new LinScript();
        Segment galleriesSegment = new AngularBracketSegment("galleries", 0);
        Element sheetElement = new AngularBracketElement("sheet");
        galleriesSegment.addElement(sheetElement);
        script.addSegment(galleriesSegment);
        sheetElement.getKeyValuePairs().put("row", sheetRow);
        sheetElement.getKeyValuePairs().put("col", sheetCol);
        return makeSequentialFrames(galleryPath, new Range(0, sheetRow*sheetCol-1), gifStartPic, gifEndPic, duration, layer, script, sheetElement);
    }

    private static Script makeSequentialFrames(String galleryPath, Range galleryPicRange, int gifStartPic, int gifEndPic,
                                               int duration, int layer, Script script, Element sheetElement) {
        sheetElement.getKeyValuePairs().put("startPic", galleryPicRange.getStart());
        sheetElement.getKeyValuePairs().put("endPic", galleryPicRange.getEnd());
        sheetElement.getKeyValuePairs().put("path", galleryPath);

        int frameTotalNumber = gifEndPic - gifStartPic + 1;
        for (int i = 0; i < frameTotalNumber; i++) {
            AngularBracketSegment frameSegment = new AngularBracketSegment("frame", i);
            frameSegment.getKeyValuePairs().put("pic", i+gifStartPic);
            frameSegment.getKeyValuePairs().put("duration", duration);
            frameSegment.getKeyValuePairs().put("next", (i + 1) % frameTotalNumber);
            frameSegment.getKeyValuePairs().put("layer", layer);
            script.addSegment(frameSegment);
        }
        return script;
    }
}
