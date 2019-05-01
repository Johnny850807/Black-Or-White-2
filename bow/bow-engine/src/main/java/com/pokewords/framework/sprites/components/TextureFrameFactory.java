package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.parsing.FrameSegment;
import com.pokewords.framework.sprites.parsing.GallerySegment;
import com.pokewords.framework.sprites.parsing.Script;
import com.pokewords.framework.views.Gallery;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Johnny850807
 */
public class TextureFrameFactory implements FrameFactory {
    private Map<String, Gallery> galleryMap = new HashMap<>();

    @Override
    public Frame createFrame(FrameSegment frameSegment) {
//        Script script = frameSegment.getScript();
//        int pic = frameSegment.getInt(FrameSegment.PIC);
//        GallerySegment gallerySegment = script.getGallerySegment(pic);
//        String path = gallerySegment.getString("path");
//        if (!galleryMap.containsKey(path))
//            galleryMap.put(path, gallerySegment.toGallery());
//        Gallery gallery = galleryMap.get(path);
//        int layer = frameSegment.getInt("layer");
//        return new TextureFrame(gallery.getImage(pic), layer);
        return null;
    }
}
