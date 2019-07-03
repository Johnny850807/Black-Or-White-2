package com.pokewords.framework.engine.parsing;

import com.pokewords.framework.engine.weaver.GameEngineWeaverNode;
import com.pokewords.framework.sprites.parsing.Segment;

import java.util.Optional;
import java.util.function.Function;

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
    private Optional<AreaElement> areaElement;
    private Optional<MoveElement> moveElement;
    private Optional<TransitionsElement> transitionsElement;
    private Optional<SpawnElement> spawnElement;

    public FrameSegment(Function<String, Object> enumProvider, Segment frameSegment) {
        this.id = frameSegment.getId();
        this.description = frameSegment.getName();
        this.pic = frameSegment.getInt("pic");
        this.layer = frameSegment.getInt("layer");
        this.duration = frameSegment.getInt("duration");
        this.next = frameSegment.getInt("next");
        this.bodyElement = Optional.ofNullable(
                frameSegment.containsElement("body")?
                new BodyElement(frameSegment.getFirstElement("body")) : null);
        this.areaElement = Optional.ofNullable(
                frameSegment.containsElement("area")?
                        new AreaElement(frameSegment.getFirstElement("area")) : null);
        this.moveElement = Optional.ofNullable(
                frameSegment.containsElement("move")?
                new MoveElement(frameSegment.getFirstElement("move")) : null);
        this.transitionsElement = Optional.ofNullable(
                frameSegment.containsElement("transitions") ?
                        new TransitionsElement(enumProvider,
                                frameSegment.getFirstElement("transitions")) : null
        );
        this.spawnElement = Optional.ofNullable(
            frameSegment.containsElement("spawn") ?
                    new SpawnElement(frameSegment.getFirstElement("spawn")) : null
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

    public Optional<AreaElement> getAreaElement() {
        return areaElement;
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
