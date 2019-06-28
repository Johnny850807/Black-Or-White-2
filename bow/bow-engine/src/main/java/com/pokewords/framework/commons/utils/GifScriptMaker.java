package com.pokewords.framework.commons.utils;

import com.pokewords.framework.commons.Range;
import com.pokewords.framework.sprites.parsing.*;

/**
 * A utility to the script that animates a GIF frame state machine component.
 * The picture number should be sequential and the first GIF picture should have frame id 0
 * @author johnny850807 (waterball)
 */
public class GifScriptMaker {

    public static Script createSequenceScript(String galleryPath,  Range galleryPicRange,
                                              int gifStartPic, int gifEndPic, int duration, int layer) {
        Script script = new LinScript();
        Segment galleriesSegment = new AngularSegment("galleries", 0);
        Element sequenceElement = new AngularElement("sequence");
        galleriesSegment.addElement(sequenceElement);
        script.addSegment(galleriesSegment);
        return makeSequentialFrames(galleryPath, galleryPicRange, gifStartPic, gifEndPic, duration, layer, script, sequenceElement);
    }

    public static Script createSheetScript(String galleryPath, int sheetRow, int sheetCol,
                                           int gifStartPic, int gifEndPic, int duration, int layer) {
        Script script = new LinScript();
        Segment galleriesSegment = new AngularSegment("galleries", 0);
        Element sheetElement = new AngularElement("sheet");
        galleriesSegment.addElement(sheetElement);
        script.addSegment(galleriesSegment);
        sheetElement.put("row", sheetRow);
        sheetElement.put("col", sheetCol);
        return makeSequentialFrames(galleryPath, new Range(0, sheetRow*sheetCol-1), gifStartPic, gifEndPic, duration, layer, script, sheetElement);
    }

    private static Script makeSequentialFrames(String galleryPath, Range galleryPicRange, int gifStartPic, int gifEndPic,
                                               int duration, int layer, Script script, Element sheetElement) {
        sheetElement.put("startPic", galleryPicRange.getStart());
        sheetElement.put("endPic", galleryPicRange.getEnd());
        sheetElement.put("path", galleryPath);

        int frameTotalNumber = gifEndPic - gifStartPic + 1;
        for (int i = 0; i < frameTotalNumber; i++) {
            AngularSegment frameSegment = new AngularSegment("frame", i);
            frameSegment.put("pic", i+gifStartPic);
            frameSegment.put("duration", duration);
            frameSegment.put("next", (i + 1) % frameTotalNumber);
            frameSegment.put("layer", layer);
            script.addSegment(frameSegment);
        }
        return script;
    }
}
