package com.pokewords.framework.engine;

import com.pokewords.framework.sprites.components.frames.EffectFrame;
import com.pokewords.framework.sprites.parsing.Segment;

import static com.pokewords.framework.sprites.parsing.ScriptDefinitions.LinScript.Segment.ID;
import static com.pokewords.framework.sprites.parsing.ScriptDefinitions.LinScript.Segment.NAME;

/**
 * @author johnny850807 (waterball)
 */
public class FrameSegment {
    private int id;
    private String frameName;
    private int pic;
    private int layer;
    private int duration;
    private int next;
    private int dvx;
    private int dvy;
    private int centerX;
    private int centerY;

    public FrameSegment(int id, String frameName, int pic, int layer, int duration, int next, int dvx, int dvy, int centerX, int centerY) {
        this.id = id;
        this.frameName = frameName;
        this.pic = pic;
        this.layer = layer;
        this.duration = duration;
        this.next = next;
        this.dvx = dvx;
        this.dvy = dvy;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public FrameSegment(Segment frameSegment) {
        this(frameSegment.getIntByKey(ID),
                frameSegment.getStringByKey(NAME),
                frameSegment.getIntByKey("pic"),
                frameSegment.getIntByKey("layer"),
                frameSegment.getIntByKey("duration"),
                frameSegment.getIntByKey("next"),
                frameSegment.getIntByKey("dvx"),
                frameSegment.getIntByKey("dvy"),
                frameSegment.getIntByKey("centerX"),
                frameSegment.getIntByKey("centerY"));
    }

    public int getId() {
        return id;
    }

    public String getFrameName() {
        return frameName;
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

    public int getDvx() {
        return dvx;
    }

    public int getDvy() {
        return dvy;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }
}
