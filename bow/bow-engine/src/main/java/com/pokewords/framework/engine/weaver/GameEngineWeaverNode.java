package com.pokewords.framework.engine.weaver;

import com.pokewords.framework.engine.Events;
import com.pokewords.framework.engine.parsing.*;
import com.pokewords.framework.ioc.IocContainer;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.frames.*;
import com.pokewords.framework.sprites.factories.SpriteWeaver;
import com.pokewords.framework.sprites.parsing.Element;
import com.pokewords.framework.sprites.parsing.Script;
import com.pokewords.framework.sprites.parsing.Segment;
import com.pokewords.framework.views.helpers.galleries.Gallery;
import com.pokewords.framework.views.helpers.galleries.GalleryFactory;

import java.util.List;
import java.util.*;

/**
 * Read the <frame> Segment and the game-engine domain attributes within the frame.
 * It does the following tasks:
 * (1) Create all effect frames of the given sprite from parsing all the <frame> segments, and add them
 * into the FrameStateMachineComponent of the sprite.
 * (2) Add the 'update' transitions from all frames corresponding to their 'next' attributes.
 * (3) Parse and effect the elements :
 *      i. <body>
 *      ii. <effect>
 *      iii. <transitions>
 * @author johnny850807 (waterball)
 */
public class GameEngineWeaverNode implements SpriteWeaver.Node {
    private IocContainer iocContainer;
    private EffectFrameFactory effectFrameFactory;

    private Sprite sprite;
    private Map<Integer, FrameSegment> frameSegmentMap = Collections.synchronizedMap(new HashMap<>()); // <FrameSegment's Id, frameSegment>

    public GameEngineWeaverNode() {
        this.effectFrameFactory = new GameEngineEffectFrameFactory();
    }

    public GameEngineWeaverNode(EffectFrameFactory effectFrameFactory) {
        this.effectFrameFactory = effectFrameFactory;
    }

    @Override
    public synchronized void onWeaving(Script script, Sprite sprite, IocContainer iocContainer) {
        if (sprite.hasComponent(FrameStateMachineComponent.class))
        {
            this.sprite = sprite;
            this.iocContainer = iocContainer;
            List<Segment> frames = script.getSegments("frame");
            frames.forEach(this::parseAndAddFrame);
            setNextTransitions();
            setAllCustomTransitions();
            cleanUpWeaverNode();
        }
    }

    private void parseAndAddFrame(Segment frame) {
        EffectFrame effectFrame = effectFrameFactory.createFrame(frame);
        sprite.getFrameStateMachineComponent().addFrame(effectFrame);
    }

    private void setNextTransitions() {
        FrameStateMachineComponent fsmc = sprite.getFrameStateMachineComponent();
        for (Integer id : frameSegmentMap.keySet()) {
            EffectFrame from = fsmc.getFrame(id);
            EffectFrame to = fsmc.getFrame(frameSegmentMap.get(id).getNext());
            fsmc.addTransition(from, Events.UPDATE, to);
        }
    }


    private void setAllCustomTransitions() {
        for (Integer id : frameSegmentMap.keySet()) {
            FrameSegment frameSegment = frameSegmentMap.get(id);
            frameSegment.getTransitionsElement()
                    .ifPresent(transitionsElement ->
                            setCustomTransitionsFromTransitionsElement(id, transitionsElement));
        }
    }

    private void setCustomTransitionsFromTransitionsElement(int frameId, TransitionsElement transitionsElement) {
        FrameStateMachineComponent fsmc = sprite.getFrameStateMachineComponent();

        for (Map.Entry<Object, Integer> entry : transitionsElement.getTransitionMap().entrySet())
        {
            EffectFrame from = fsmc.getFrame(frameId);
            Object event = entry.getKey();
            int toId = entry.getValue();
            EffectFrame to = fsmc.getFrame(toId);
            fsmc.addTransition(from, event, to);
        }
    }

    private void cleanUpWeaverNode() {
        this.sprite = null;
        this.iocContainer = null;
        this.frameSegmentMap.clear();
    }

    public class GameEngineEffectFrameFactory implements EffectFrameFactory {
        // < <startPic/endPic>, gallery instance>
        private Set<Gallery> gallerySet;

        @Override
        public EffectFrame createFrame(Segment segment) {
            setupGalleryMapIfNotExists((Script) segment.getParent());
            FrameSegment frameSegment = new FrameSegment(segment);
            EffectFrame effectFrame = createAndPutImageEffectFrame(frameSegment);
            GameEffect assembledGameEffect = GameEffect.assemble(
                    parseEffectElementGameEffect(frameSegment),
                    parseBodyElementGameEffect(frameSegment)
            );
            effectFrame.addEffect(assembledGameEffect);
            return effectFrame;
        }


        private void setupGalleryMapIfNotExists(Script script) {
            if (gallerySet == null) {
                synchronized (this) {
                    if (gallerySet == null) {
                        gallerySet = Collections.synchronizedSet(new HashSet<>());
                        Segment galleriesSegment = script.getFirstSegment("galleries");
                        addAllGalleriesToGalleryMap(galleriesSegment);
                    }
                }
            }
        }

        private void addAllGalleriesToGalleryMap(Segment galleriesSegment) {
            GalleryFactory galleryFactory = iocContainer.galleryFactory();
            for (Element element : galleriesSegment.getElements()) {
                gallerySet.add(galleryFactory.create(element.getName(), element.pack()));
            }
        }

        private EffectFrame createAndPutImageEffectFrame(FrameSegment frameSegment) {
            frameSegmentMap.put(frameSegment.getId(), frameSegment);
            Gallery targetGallery = locateGallery(frameSegment.getPic());
            ImageFrame imageFrame = ImageFrameFactory.fromGallery(frameSegment.getLayer(),
                    targetGallery, frameSegment.getPic());
            return EffectFrame.wrap(imageFrame, frameSegment.getId(), frameSegment.getDuration());

        }

        private Gallery locateGallery(int pic) {
            return gallerySet.stream()
                    .filter(g -> g.containsPicture(pic))
                    .findFirst()
                    .orElseThrow(()-> new IllegalArgumentException("The pic number " + pic + " is not found in any gallery range."));
        }

        private GameEffect parseBodyElementGameEffect(FrameSegment frameSegment) {
            if (frameSegment.getBodyElement().isPresent())
            {
                BodyElement bodyElement = frameSegment.getBodyElement().get();
                return (world, sprite) -> {
                    int x = bodyElement.getX().orElse(sprite.getX());
                    int y = bodyElement.getY().orElse(sprite.getY());
                    int w = bodyElement.getW().orElse(sprite.getWidth());
                    int h = bodyElement.getH().orElse(sprite.getHeight());
                    int centerX = bodyElement.getCenterX().orElse((int) sprite.getCenter().getX());
                    int centerY = bodyElement.getCenterY().orElse((int) sprite.getCenter().getY());
                    sprite.setBody(x, y, w, h);
                    sprite.setCenter(centerX, centerY);
                };
            }
            else
                return GameEffect.empty();
        }


        private GameEffect parseEffectElementGameEffect(FrameSegment frameSegment) {
            if (frameSegment.getEffectElement().isPresent())
            {
                EffectElement effectElement = frameSegment.getEffectElement().get();
                return (world, sprite) -> {
                    effectElement.getMoveX().ifPresent(sprite::moveX);
                    effectElement.getMoveY().ifPresent(sprite::moveY);
                };
            }
            else
                return GameEffect.empty();
        }
    }

}
