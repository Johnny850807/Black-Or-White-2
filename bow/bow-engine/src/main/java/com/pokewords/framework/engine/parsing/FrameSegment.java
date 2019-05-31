package com.pokewords.framework.engine.parsing;

import com.pokewords.framework.sprites.parsing.AngularBracketSegment;
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
    private Optional<MoveElement> moveElement;
    private Optional<TransitionsElement> transitionsElement;

    public FrameSegment(Segment frameSegment) {
        this.id = ((AngularBracketSegment) frameSegment).getId();
        this.description = frameSegment.getName();
        this.pic = frameSegment.getKeyValuePairs().getInt("pic");
        this.layer = frameSegment.getKeyValuePairs().getInt("layer");
        this.duration = frameSegment.getKeyValuePairs().getInt("duration");
        this.next = frameSegment.getKeyValuePairs().getInt("next");
        this.bodyElement = Optional.ofNullable(
                frameSegment.containsElement("body")?
                new BodyElement(frameSegment.getFirstElement("body")) : null);
        this.moveElement = Optional.ofNullable(
                frameSegment.containsElement("move")?
                new MoveElement(frameSegment.getFirstElement("move")) : null);
        this.transitionsElement = Optional.ofNullable(
                frameSegment.containsElement("transitions") ?
                        new TransitionsElement(frameSegment.getFirstElement("transitions")) : null
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

    public Optional<MoveElement> getMoveElement() {
        return moveElement;
    }

    public Optional<TransitionsElement> getTransitionsElement() {
        return transitionsElement;
    }
}
