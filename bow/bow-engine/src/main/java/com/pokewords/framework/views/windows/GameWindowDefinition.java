package com.pokewords.framework.views.windows;

import java.awt.*;

public class GameWindowDefinition {
    public String name = "Default";
    public Dimension size = new Dimension(0, 0);
    public Point location = new Point(0, 0);
    public Color gamePanelBackground = Color.black;

    public Point center() {
        return new Point((int) size.getWidth() / 2, (int) size.getHeight() / 2);
    }

    public Point center(Point relativeToSize) {
        return new Point((int) size.getWidth() / 2 - relativeToSize.x / 2, (int) size.getHeight() / 2 - relativeToSize.y / 2);
    }
}
