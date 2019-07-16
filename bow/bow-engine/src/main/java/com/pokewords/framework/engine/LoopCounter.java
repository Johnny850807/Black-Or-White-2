package com.pokewords.framework.engine;

import com.pokewords.framework.engine.listeners.GameLoopingListener;

import java.util.Collection;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArraySet;

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
        System.out.println("Loop: " + loopNumber);
        if (actionMap.containsKey(loopNumber))
        {
            System.out.println("### Run on " + loopNumber);
            actionMap.get(loopNumber).forEach(Runnable::run);
            actionMap.remove(loopNumber);
        }
    }

    public void addLoopCountdownHook(long countDownLoopNumber, Runnable hook) {
        countDownLoopNumber = countDownLoopNumber == 0 ? 1 : countDownLoopNumber;
        long loop = loopNumber + countDownLoopNumber;
        if (!actionMap.containsKey(loop))
            actionMap.put(loop, new CopyOnWriteArraySet<>());
        actionMap.get(loop).add(hook);
    }

    public void removeLoopCountdownHook(Runnable action) {
        for (Long loop : actionMap.keySet()) {
            actionMap.get(loop).remove(action);
        }
    }

    public long getCurrentLoopNumber() {
        return loopNumber;
    }
}
