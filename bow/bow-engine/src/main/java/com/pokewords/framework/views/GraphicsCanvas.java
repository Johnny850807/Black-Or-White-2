package com.pokewords.framework.views;

import java.awt.*;

public class GraphicsCanvas implements Canvas {
    private static GraphicsCanvas canvas = new GraphicsCanvas();
    private Graphics  graphics;

    public static Canvas of(Graphics graphics){
        canvas.graphics = graphics;
        return canvas;
    }

    @Override
    public void renderImage(int x, int y, Image image) {
        canvas.graphics.drawImage(image, x, y, null);
    }

    @Override
    public void renderText(int x, int y, String text, Color color, Font font) {
        canvas.graphics.setColor(color);
        canvas.graphics.setFont(font);
        canvas.graphics.drawString(text, x, y);
    }

    @Override
    public void renderImageByCenter(int x, int y, Image image) {
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        canvas.graphics.drawImage(image, x-imageWidth/2, y-imageHeight/2, null);
    }

    @Override
    public void renderTextByCenter(int x, int y, String text, Color color, Font font) {
        canvas.graphics.setColor(color);
        canvas.graphics.setFont(font);
        int width = canvas.graphics.getFontMetrics().stringWidth(text);
        int height = canvas.graphics.getFontMetrics().getHeight();
        canvas.graphics.drawString(text, x-width/2, y-height/2);
    }

}
