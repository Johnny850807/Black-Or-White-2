package com.pokewords.framework.engine.parsing;

import com.pokewords.framework.sprites.parsing.Element;
import com.pokewords.framework.sprites.parsing.Segment;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static com.pokewords.framework.sprites.parsing.ScriptDefinitions.LinScript.Segment.ID;
import static com.pokewords.framework.sprites.parsing.ScriptDefinitions.LinScript.Segment.NAME;

/**
 * @author johnny850807 (waterball)
 */
public class FrameSegment {
    private int id;
    private String description;
    private int pic;
    private int layer;
    private int duration;
    private int next;
    private @Nullable PropertiesElement propertiesElement;
    private @Nullable EffectElement effectElement;

    public FrameSegment(int id, String description, int pic, int layer, int duration, int next,
                        @Nullable Element propertiesElement, @Nullable Element effectElement) {
        this.id = id;
        this.description = description;
        this.pic = pic;
        this.layer = layer;
        this.duration = duration;
        this.next = next;
        this.propertiesElement = propertiesElement == null ? null : new PropertiesElement(propertiesElement);
        this.effectElement = effectElement == null ? null : new EffectElement(effectElement);
    }

    public FrameSegment(Segment frameSegment) {
        this.id = frameSegment.getIntByKey(ID);
        this.description = frameSegment.getStringByKey(NAME);
        this.pic = frameSegment.getIntByKey("pic");
        this.layer = frameSegment.getIntByKey("layer");
        this.duration = frameSegment.getIntByKey("duration");
        this.next = frameSegment.getIntByKey("next");

        Element propertiesElement = frameSegment.getElement("properties");
        Element effectElement = frameSegment.getElement("effect");
        this.propertiesElement = propertiesElement == null ? null : new PropertiesElement(propertiesElement);
        this.effectElement = effectElement == null ? null : new EffectElement(effectElement);
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

    public @Nullable PropertiesElement getPropertiesElement() {
        return propertiesElement;
    }

    public @Nullable EffectElement getEffectElement() {
        return effectElement;
    }

    public Optional<PropertiesElement> getPropertiesElementOptional() {
        return Optional.ofNullable(propertiesElement);
    }

    public Optional<EffectElement> getEffectElementOptional() {
        return Optional.ofNullable(effectElement);
    }
}
