package com.pokewords.framework.commons.utils;

import com.pokewords.framework.sprites.parsing.*;

/**
 * A utility to create the script that animates a GIF frame state machine component.
 * The picture and frame number should be sequential and the first GIF picture should have frame id 0
 * @author johnny850807 (waterball)
 */
public class GifScriptMaker {
    public static Script createScript(String galleryType, String galleryPath, int duration,
                                      int frameTotalNumber, int layer) {
        return createScript(galleryType, galleryPath, 0, duration, frameTotalNumber, layer);
    }

    public static Script createScript(String galleryType, String galleryPath, int startPic, int duration,
                                       int frameTotalNumber, int layer) {
        Script script = new LinScript();
        Segment galleriesSegment = new LinScriptSegment("galleries", 0);
        Element sequenceElement = new LinScriptElement(galleryType);
        sequenceElement.put("startPic", startPic);
        sequenceElement.put("endPic", startPic+frameTotalNumber-1);
        sequenceElement.put("path", galleryPath);
        script.addSegment(galleriesSegment);
        galleriesSegment.addElement(sequenceElement);

        for (int i = startPic; i < frameTotalNumber; i++) {
            LinScriptSegment frameSegment = new LinScriptSegment("frame", i);
            frameSegment.put("pic", i);
            frameSegment.put("duration", duration);
            frameSegment.put("next", (i + 1) % frameTotalNumber);
            frameSegment.put("layer", layer);
            script.addSegment(frameSegment);
        }
        return script;
    }
}
