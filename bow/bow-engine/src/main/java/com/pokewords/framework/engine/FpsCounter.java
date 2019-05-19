package com.pokewords.framework.engine;

import java.util.Arrays;

public class FpsCounter {
    private final static int MAX_SAMPLES = 100;

    private long[] frameTimes = new long[MAX_SAMPLES];
    private int index = 0;
    private boolean arrayFilled = false;
    private int fps = 0;

    public int update(long now) {
        long oldFrameTime = frameTimes[index];
        frameTimes[index] = now;
        index = (index + 1) % frameTimes.length;

        if (index == 0) {
            arrayFilled = true;
        }

        if (arrayFilled) {
            long elapsedNanos = now - oldFrameTime;
            long elapsedNanosPerFrame = elapsedNanos / frameTimes.length;
            fps = (int)(1_000_000_000.0 / elapsedNanosPerFrame);
        }

        return fps;
    }

    void reset() {
        Arrays.fill(frameTimes, 0);
        index = 0;
        arrayFilled = false;
        fps = 0;
    }
}
