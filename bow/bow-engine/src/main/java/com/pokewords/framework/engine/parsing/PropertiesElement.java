package com.pokewords.framework.engine.parsing;

import com.pokewords.framework.sprites.parsing.Element;

/**
 * @author johnny850807 (waterball)
 */
public class PropertiesElement {
    private int x;
    private int y;
    private int w;
    private int h;
    private int centerX;
    private int centerY;

    public PropertiesElement(int x, int y, int w, int h, int centerX, int centerY) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public PropertiesElement(Element element) {
        this(element.getIntByKey("x"),
                element.getIntByKey("y"),
                element.getIntByKey("w"),
                element.getIntByKey("h"),
                element.getIntByKey("centerX"),
                element.getIntByKey("centerY"));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }
}
