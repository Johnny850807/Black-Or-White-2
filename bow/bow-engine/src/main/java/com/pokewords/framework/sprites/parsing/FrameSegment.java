package com.pokewords.framework.sprites.parsing;

/**
 *
 *  TODO: 保留直到 Segment 可以如 FrameSegment 方便使用
 *        暫時留下 Segment 做不到/不方便的部分
 */
public class FrameSegment {

    public final static String PIC = "pic";
    public final static String DURATION = "duration";
    public final static String NEXT = "next";

    // Segment Info
    private int frameNumber;
    private String frameName;

    public FrameSegment(LinScript script, int frameNumber, String frameName) {
        this.frameNumber = frameNumber;
        this.frameName = frameName;
    }

    // Getters

    public int getFrameNumber(){
        return frameNumber;
    }

    public String getFrameName() {
        return frameName;
    }

}
