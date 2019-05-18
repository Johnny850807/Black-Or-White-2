package com.pokewords.framework.engine.weaver;

import com.pokewords.framework.commons.Range;
import com.pokewords.framework.engine.Events;
import com.pokewords.framework.engine.parsing.EffectElement;
import com.pokewords.framework.engine.parsing.FrameSegment;
import com.pokewords.framework.engine.parsing.GalleryElement;
import com.pokewords.framework.engine.parsing.PropertiesElement;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.frames.EffectFrame;
import com.pokewords.framework.sprites.components.frames.EffectFrameFactory;
import com.pokewords.framework.sprites.components.frames.ImageEffectFrame;
import com.pokewords.framework.sprites.factories.SpriteWeaver;
import com.pokewords.framework.sprites.parsing.Element;
import com.pokewords.framework.sprites.parsing.Script;
import com.pokewords.framework.sprites.parsing.Segment;
import com.pokewords.framework.views.helpers.galleries.Gallery;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Read the <frame> Segment and the game-engine domain attributes within the frame.
 * It does the following tasks:
 * (1) Create all effect frames of the given sprite from parsing all the <frame> segments, and add them
 * into the FrameStateMachineComponent of the sprite.
 * (2) Add the 'update' transitions from all frames corresponding to their 'next' attributes.
 * (3) Parse and effect the elements :
 *      i. <properties>
 *      ii. <effect>
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
            frames.forEach(f -> parseAndAddFrame(f, sprite));
            setNextTransitions(sprite);
        }

    }

    private void parseAndAddFrame(Segment frame, Sprite sprite) {
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
        private Set<Gallery> gallerySet;

        @Override
        public EffectFrame createFrame(Segment segment) {
            setupGalleryMapIfNotExists(segment.getParentScript());
            FrameSegment frameSegment = new FrameSegment(segment);
            EffectFrame effectFrame = initEffectFrame(frameSegment, segment);
            parsePropertiesElement(frameSegment, effectFrame);
            parseEffectElement(frameSegment, effectFrame);
            return effectFrame;
        }

        private void setupGalleryMapIfNotExists(Script script) {
            if (gallerySet == null) {
                synchronized (this) {
                    if (gallerySet == null) {
                        gallerySet = Collections.synchronizedSet(new HashSet<>());
                        Segment galleriesSegment = script.getSegmentByName("galleries");
                        addAllGalleriesToGalleryMap(galleriesSegment);
                    }
                }
            }
        }

        private void addAllGalleriesToGalleryMap(Segment galleriesSegment) {
            for (Element element : galleriesSegment.getElements()) {
                GalleryElement galleryElement = new GalleryElement(element);
                gallerySet.add(galleryElement.toGallery());
            }
        }

        private EffectFrame initEffectFrame(FrameSegment frameSegment, Segment segment) {
            frameSegmentMap.put(frameSegment.getId(), frameSegment);
            return new ImageEffectFrame(frameSegment.getId(), frameSegment.getLayer(), frameSegment.getDuration(),
                    getImage(frameSegment.getPic()));
        }

        private synchronized Image getImage(int pic) {
            for (Gallery gallery : gallerySet)
                if (gallery.containsPicture(pic))
                    return gallery.getImage(pic);

            throw new IllegalArgumentException(String.format("The pic %d is not within any galleries.", pic));
        }
    }

    private void parsePropertiesElement(FrameSegment frameSegment, EffectFrame effectFrame) {
        frameSegment.getPropertiesElement()
                .ifPresent(p -> effectPropertiesElement(p, effectFrame));
    }

    private void effectPropertiesElement(PropertiesElement element, EffectFrame effectFrame) {
        effectFrame.addEffect((world, sprite) -> {
            int x = element.getX().orElse(sprite.getX());
            int y = element.getY().orElse(sprite.getY());
            int w = element.getW().orElse(sprite.getWidth());
            int h = element.getH().orElse(sprite.getHeight());
            int centerX = element.getCenterX().orElse((int) sprite.getCenter().getX());
            int centerY = element.getCenterY().orElse((int) sprite.getCenter().getY());
            sprite.setBody(x, y, w, h);
            sprite.setCenter(centerX, centerY);
        });
    }

    private void parseEffectElement(FrameSegment frameSegment, EffectFrame effectFrame) {
        frameSegment.getEffectElement()
                .ifPresent(e -> effectEffectElement(e, effectFrame));
    }

    private void effectEffectElement(EffectElement element, EffectFrame effectFrame) {
        effectFrame.addEffect((world, sprite) -> {
            element.getMoveX().ifPresent(sprite::moveX);
            element.getMoveY().ifPresent(sprite::moveY);
        });
    }

}
