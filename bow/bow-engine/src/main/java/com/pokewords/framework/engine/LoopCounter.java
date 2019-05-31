package com.pokewords.framework.engine;

import com.pokewords.framework.engine.listeners.GameLoopingListener;

import java.util.Collection;
import java.util.HashSet;
import java.util.TreeMap;

/**
 * The public common Loop number updater.
 * @author johnny850807 (waterball)
 */
public class LoopCounter implements GameLoopingListener {
    private long loopNumber = 0;
    private TreeMap<Long, Collection<Runnable>> actionMap = new TreeMap<>();

    @Override
    public void onUpdate(double timePerFrame) {
        loopNumber = (loopNumber +1) % Long.MAX_VALUE;

        if (actionMap.containsKey(loopNumber))
        {
            actionMap.get(loopNumber).forEach(Runnable::run);
            actionMap.remove(loopNumber);
        }
    }

    public void doAfterLoopCountDown(long countDownLoopNumber, Runnable action) {
        countDownLoopNumber = countDownLoopNumber == 0 ? 1 : countDownLoopNumber;
        long loop = loopNumber + countDownLoopNumber;
        if (!actionMap.containsKey(loop))
            actionMap.put(loop, new HashSet<>());
        actionMap.get(loop).add(action);
    }

    public long getCurrentLoopNumber() {
        return loopNumber;
    }
}
