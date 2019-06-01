package com.pokewords.framework.engine.parsing;

import com.pokewords.framework.sprites.parsing.Element;

import java.util.Scanner;

/**
 * @author johnny850807 (waterball)
 */
public class SpawnElement {
    private int x;
    private int y;
    private int frameId;
    private Object type;

    public SpawnElement(Element element) {
        this.x = element.getInt("x");
        this.y = element.getInt("y");
        this.frameId = element.getInt("frameId");
        this.type = null;  //TODO
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getFrameId() {
        return frameId;
    }

}
