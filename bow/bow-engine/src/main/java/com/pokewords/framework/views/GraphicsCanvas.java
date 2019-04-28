package com.pokewords.framework.views;

import java.awt.*;

public class GraphicsCanvas implements Canvas{
    private static GraphicsCanvas canvas = new GraphicsCanvas();
    private Graphics graphics;

    public static Canvas of(Graphics graphics){
        canvas.graphics = graphics;
        return canvas;
    }

    @Override
    public void render(int x, int y, Image image) {
        canvas.graphics.drawImage(image, x, y, null);
    }

}
