package com.pokewords.framework.engine;

import com.pokewords.framework.commons.utils.ArrayUtility;
import com.pokewords.framework.engine.listeners.GameLoopingListener;

import java.util.Arrays;

public class FpsCounter {
    private final static int MAX_SAMPLES = 100;

    private long[] frameTimes;
    private int index = 0;
    private double timePerFrame = 0.016;

    public FpsCounter() {
        frameTimes = new long[MAX_SAMPLES];
    }

    public double update() {
        if (frameTimes[index] == 0)
            frameTimes[index] = 16;
        else
            frameTimes[index] = System.currentTimeMillis() - frameTimes[index];
        index = (index + 1) % frameTimes.length;

        if (index == 0)
            timePerFrame = ArrayUtility.average(frameTimes) / 1000;


        frameTimes[index] = System.currentTimeMillis();
        return 1 / timePerFrame;
    }

    public void reset() {
        Arrays.fill(frameTimes, 0);
        index = 0;
        timePerFrame = 0;
    }

    public double getAverageTimePerFrame() {
        return timePerFrame;
    }

    public double getAverageFramesPerSecond() {
        return 1 / timePerFrame;
    }
}
