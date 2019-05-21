package com.pokewords.framework.engine.parsing;

import com.pokewords.framework.sprites.parsing.Segment;

import java.util.Optional;

/**
 * @author johnny850807 (waterball)
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class FrameSegment {
    private int id;
    private String description;
    private int pic;
    private int layer;
    private int duration;
    private int next;
    private Optional<BodyElement> bodyElement;
    private Optional<EffectElement> effectElement;
    private Optional<TransitionsElement> transitionsElement;

    public FrameSegment(Segment frameSegment) {
        this.id = frameSegment.getId();
        this.description = frameSegment.getName();
        this.pic = frameSegment.getInt("pic");
        this.layer = frameSegment.getInt("layer");
        this.duration = frameSegment.getInt("duration");
        this.next = frameSegment.getInt("next");
        this.bodyElement = Optional.ofNullable(
                frameSegment.containsElement("body")?
                new BodyElement(frameSegment.getElement("body")) : null);
        this.effectElement = Optional.ofNullable(
                frameSegment.containsElement("effect")?
                new EffectElement(frameSegment.getElement("effect")) : null);
        this.transitionsElement = Optional.ofNullable(
                frameSegment.containsElement("transitions") ?
                        new TransitionsElement(frameSegment.getElement("transitions")) : null
        );
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getPic() {
        return pic;
    }

    public int getLayer() {
        return layer;
    }

    public int getDuration() {
        return duration;
    }

    public int getNext() {
        return next;
    }


    public Optional<BodyElement> getBodyElement() {
        return bodyElement;
    }

    public Optional<EffectElement> getEffectElement() {
        return effectElement;
    }

    public Optional<TransitionsElement> getTransitionsElement() {
        return transitionsElement;
    }
}
