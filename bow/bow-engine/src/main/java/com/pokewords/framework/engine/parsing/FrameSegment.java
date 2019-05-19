package com.pokewords.framework.engine.parsing;

import com.pokewords.framework.sprites.parsing.Element;
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
    private Optional<PropertiesElement> propertiesElement;
    private Optional<EffectElement> effectElement;

    public FrameSegment(int id, String description, int pic, int layer, int duration, int next,
                        Element propertiesElement, Element effectElement) {
        this.id = id;
        this.description = description;
        this.pic = pic;
        this.layer = layer;
        this.duration = duration;
        this.next = next;
        this.propertiesElement = Optional.of(new PropertiesElement(propertiesElement));
        this.effectElement = Optional.of(new EffectElement(effectElement));
    }

    public FrameSegment(Segment frameSegment) {
        this.id = frameSegment.getId();
        this.description = frameSegment.getName();
        this.pic = frameSegment.getIntByKey("pic");
        this.layer = frameSegment.getIntByKey("layer");
        this.duration = frameSegment.getIntByKey("duration");
        this.next = frameSegment.getIntByKey("next");
        this.propertiesElement = Optional.ofNullable(
                frameSegment.containsElementName("properties")?
                new PropertiesElement(frameSegment.getElementByName("properties")) : null);
        this.effectElement = Optional.ofNullable(
                frameSegment.containsElementName("effect")?
                new EffectElement(frameSegment.getElementByName("effect")) : null);
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


    public Optional<PropertiesElement> getPropertiesElement() {
        return propertiesElement;
    }

    public Optional<EffectElement> getEffectElement() {
        return effectElement;
    }
}
