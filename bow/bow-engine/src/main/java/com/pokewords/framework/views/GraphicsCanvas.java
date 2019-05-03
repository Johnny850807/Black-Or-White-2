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
    public void renderImage(int x, int y, Image image) {
        canvas.graphics.drawImage(image, x, y, null);
    }

    @Override
    public void renderText(int x, int y, String text) {
        canvas.graphics.drawString(text, x, y);
    }

}
