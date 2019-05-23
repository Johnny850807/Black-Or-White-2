package com.pokewords.framework.commons.utils;

public class ArrayUtility {
    
    public static double average(long[] nums) {
        double sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        return sum / nums.length;
    }
}
