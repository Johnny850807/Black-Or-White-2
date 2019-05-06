package com.pokewords.framework.views.windows;

import java.awt.*;

public class GameWindowDefinition {
    public String name;
    public Point size;
    public Point location;
    public Color gamePanelBackground;

    public Point center() {
        return new Point(size.x/2, size.y/2);
    }
    public Point center(Point relativeToSize) {
        return new Point(size.x/2 - relativeToSize.x/2, size.y/2 - relativeToSize.y/2);
    }
}
