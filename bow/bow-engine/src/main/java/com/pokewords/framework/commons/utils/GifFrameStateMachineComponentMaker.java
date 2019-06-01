package com.pokewords.framework.commons.utils;

import com.pokewords.framework.commons.Range;
import com.pokewords.framework.engine.Events;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.frames.EffectFrame;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.components.frames.ImageFrame;
import com.pokewords.framework.sprites.components.frames.ImageFrameFactory;
import com.pokewords.framework.views.helpers.galleries.Gallery;
import com.pokewords.framework.views.helpers.galleries.SequenceGallery;
import com.pokewords.framework.views.helpers.galleries.SheetGallery;

import java.util.stream.IntStream;

/**
 * A utility that create a frame state machine component to animate a GIF.
 * The picture number of the gallery should be sequential and the first GIF picture will have frame id 0
 * @author johnny850807 (waterball)
 */
public class GifFrameStateMachineComponentMaker {
    public static FrameStateMachineComponent fromSequence(String galleryPath, Range galleryPicRange,
                                                          int gifStartPic, int gifEndPic, int duration, int layer)  {
        SequenceGallery sequenceGallery = new SequenceGallery(galleryPath, galleryPicRange);
        FrameStateMachineComponent fsmc = new FrameStateMachineComponent();

        createAndAddAllFramesFromGallery(fsmc, sequenceGallery, gifStartPic, gifEndPic, duration, layer);
        addNextTransitionsFromAllFrames(fsmc, gifStartPic, gifEndPic);
        return fsmc;
    }

    public static FrameStateMachineComponent fromSheet(String galleryPath, int sheetRow, int sheetCol,
                                                       int gifStartPic, int gifEndPic, int duration, int layer) {
        SheetGallery sheetGallery = new SheetGallery(galleryPath, new Range(0, sheetRow*sheetCol-1),
                sheetRow, sheetCol);
        FrameStateMachineComponent fsmc = new FrameStateMachineComponent();

        createAndAddAllFramesFromGallery(fsmc, sheetGallery, gifStartPic, gifEndPic, duration, layer);
        addNextTransitionsFromAllFrames(fsmc, gifStartPic, gifEndPic);
        return fsmc;
    }

    private static void createAndAddAllFramesFromGallery(FrameStateMachineComponent fsmc, Gallery gallery, int gifStartPic, int gifEndPic,
                                                         int duration, int layer) {
        IntStream.range(gifStartPic, gifEndPic+1).boxed()
                .map(pic -> EffectFrame.wrap(
                        ImageFrameFactory.fromGallery(layer, gallery, pic), pic, duration))
                .forEach(fsmc::addFrame);
    }

    private static void addNextTransitionsFromAllFrames(FrameStateMachineComponent fsmc, int gifStartPic, int gifEndPic) {
        IntStream.range(gifStartPic, gifEndPic+1).boxed()
                .forEach(pic -> {
                    EffectFrame fromFrame = fsmc.getFrame(pic);
                    EffectFrame toFrame = fsmc.getFrame(pic == gifEndPic ? gifStartPic : pic+1);
                    fsmc.addTransition(fromFrame, Events.UPDATE, toFrame);
                });
    }


}
