package com.pokewords.framework.engine;

import com.pokewords.framework.commons.Range;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.frames.EffectFrame;
import com.pokewords.framework.sprites.components.frames.EffectFrameFactory;
import com.pokewords.framework.sprites.components.frames.ImageEffectFrame;
import com.pokewords.framework.sprites.factories.SpriteWeaver;
import com.pokewords.framework.sprites.parsing.Script;
import com.pokewords.framework.sprites.parsing.Segment;
import com.pokewords.framework.views.helpers.Gallery;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Read the <frame> Segment and the game-engine domain attributes within the frame.
 * It does the following tasks:
 * (1) Create all effect frames of the given sprite from parsing all the <frame> segments, and add them
 * into the FrameStateMachineComponent of the sprite.
 * (2) Add the 'update' transitions from all frames corresponding to their 'next' attributes.
 * (3) TODO Read the <body> element within <frame> and effect the body element in each frame
 * @author johnny850807 (waterball)
 */
public class GameEngineWeaverNode implements SpriteWeaver.Node {
    private EffectFrameFactory effectFrameFactory;

    // <FrameSegment's Id, frameSegment>
    private Map<Integer, FrameSegment> frameSegmentMap = Collections.synchronizedMap(new HashMap<>());

    public GameEngineWeaverNode() {
        this.effectFrameFactory = new GameEngineEffectFrameFactory();
    }

    public GameEngineWeaverNode(EffectFrameFactory effectFrameFactory) {
        this.effectFrameFactory = effectFrameFactory;
    }

    @Override
    public void onWeaving(Script script, Sprite sprite) {
        if (sprite.hasComponent(FrameStateMachineComponent.class))
        {
            List<Segment> frames = script.getSegmentsByName("frame");
            frames.parallelStream().forEach(f -> addFrame(f, sprite));
            setNextTransitions(sprite);
        }

    }

    private void addFrame(Segment frame, Sprite sprite) {
        EffectFrame effectFrame = effectFrameFactory.createFrame(frame);
        sprite.getFrameStateMachineComponent().addFrame(effectFrame);
    }

    private void setNextTransitions(Sprite sprite) {
        FrameStateMachineComponent fsmc = sprite.getFrameStateMachineComponent();
        for (Integer id : frameSegmentMap.keySet()) {
            EffectFrame from = fsmc.getFrame(id);
            EffectFrame to = fsmc.getFrame(frameSegmentMap.get(id).getNext());
            fsmc.addTransition(from, Events.UPDATE, to);
        }
    }

    public class GameEngineEffectFrameFactory implements EffectFrameFactory {
        // < <startPic/endPic>, gallery instance>
        private Map<Range, Gallery> galleryMap;

        @Override
        public EffectFrame createFrame(Segment segment) {
            setupGalleryMapIfNotExists(segment.getParentScript());
            FrameSegment frameSegment = new FrameSegment(segment);
            frameSegmentMap.put(frameSegment.getId(), frameSegment);

            return new ImageEffectFrame(frameSegment.getId(), frameSegment.getLayer(), frameSegment.getDuration(),
                    getImage(frameSegment.getPic()));
        }

        private void setupGalleryMapIfNotExists(Script script) {
            if (galleryMap == null) {
                synchronized (this) {
                    if (galleryMap == null) {
                        galleryMap = Collections.synchronizedMap(new HashMap<>());
                        List<Segment> gallerySegments = script.getSegmentsByName("gallery");
                        for (Segment gallerySegment : gallerySegments) {
                            galleryMap.put(new Range(gallerySegment.getIntByKey("startPic"), gallerySegment.getIntByKey("endPic")),
                                    gallerySegmentToGallery(gallerySegment));
                        }
                    }
                }
            }
        }

        private Gallery gallerySegmentToGallery(Segment gallerySegment) {
            return new Gallery(gallerySegment.getStringByKey("path"),
                    gallerySegment.getIntByKey("row"),
                    gallerySegment.getIntByKey("col"),
                    gallerySegment.getIntByKey("padding"));
        }


        private Image getImage(int pic) {
            for (Range range : galleryMap.keySet())
                if(range.within(pic))
                    return galleryMap.get(range).getImage(pic);

            throw new IllegalArgumentException(String.format("The pic %d is not within any galleries.", pic));
        }
    }
}
