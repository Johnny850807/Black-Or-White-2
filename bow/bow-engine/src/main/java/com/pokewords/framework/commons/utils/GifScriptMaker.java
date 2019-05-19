package com.pokewords.framework.commons.utils;

import com.pokewords.framework.sprites.parsing.*;

public class GifScriptMaker {
    public static Script createScript(String galleryType, String galleryPath, int duration,
                                       int frameTotalNumber, int layer) {
        Script script = new LinScript();
        Segment galleriesSegment = new LinScriptSegment("galleries", 0);
        Element sequenceElement = new LinScriptElement(galleryType);
        sequenceElement.put("startPic", 0);
        sequenceElement.put("endPic", frameTotalNumber-1);
        sequenceElement.put("path", galleryPath);
        script.addSegment(galleriesSegment);
        galleriesSegment.addElement(sequenceElement);

        for (int i = 0; i < frameTotalNumber; i++) {
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
