package com.pokewords.framework.commons.utils;

public class ThreadUtility {

    /**
     * Thread.sleep(ms)
     */
    public static void delay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
