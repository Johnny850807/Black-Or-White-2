package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.sprites.parsing.FrameSegment;
import com.pokewords.framework.views.helpers.Gallery;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Johnny850807
 */
public class TextureFrameFactory implements FrameFactory {
    private Map<String, Gallery> galleryMap = new HashMap<>();

    @Override
    public Frame createFrame(FrameSegment frameSegment) {
//        LinScript script = frameSegment.getScript();
//        int pic = frameSegment.getInt(FrameSegment.PIC);
//        GallerySegment gallerySegment = script.getGallerySegment(pic);
//        String path = gallerySegment.getString("path");
//        if (!galleryMap.containsKey(path))
//            galleryMap.put(path, gallerySegment.toGallery());
//        Gallery gallery = galleryMap.get(path);
//        int layer = frameSegment.getInt("layer");
//        return new TextureEffectFrame(gallery.getImage(pic), layer);
        return null;
    }
}
