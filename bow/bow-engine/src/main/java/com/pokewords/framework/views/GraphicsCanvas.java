package com.pokewords.framework.views;

import com.pokewords.framework.sprites.components.frames.ImageFrame;
import com.pokewords.framework.sprites.components.frames.RectangleFrame;
import com.pokewords.framework.sprites.components.frames.StringFrame;

import java.awt.*;

/**
 * A singleton implementation of Canvas that tight coupled with Swing framework's Graphics.
 * @author johnny850807 (waterball)
 */
public class GraphicsCanvas implements Canvas {
    private static GraphicsCanvas canvas = new GraphicsCanvas();
    private Graphics graphics;

    public static Canvas of(Graphics graphics) {
        canvas.graphics = graphics;
        return canvas;
    }

    @Override
    public Dimension render(StringFrame stringFrame) {
        FontMetrics metrics = canvas.graphics.getFontMetrics();
        int width = metrics.stringWidth(stringFrame.getText());
        int height = metrics.getHeight();

        canvas.graphics.setColor(stringFrame.getColor());
        canvas.graphics.setFont(stringFrame.getFont());
        if (stringFrame.hasFlag(StringFrame.CANVAS_FLAG_RENDER_BY_CENTER))
            canvas.graphics.drawString(stringFrame.getText(), stringFrame.getX() - width / 2, stringFrame.getY() - height / 2);
        else
            canvas.graphics.drawString(stringFrame.getText(), stringFrame.getX(), stringFrame.getY() + metrics.getAscent());
        return null;
    }


    @Override
    public void render(ImageFrame imageFrame) {
        if (imageFrame.hasFlag(ImageFrame.CANVAS_FLAG_RENDER_BY_CENTER))
            canvas.graphics.drawImage(imageFrame.getImage(), imageFrame.getX()-imageFrame.getWidth()/2,
                    imageFrame.getY()-imageFrame.getHeight()/2, null);
        else
            canvas.graphics.drawImage(imageFrame.getImage(), imageFrame.getX(), imageFrame.getY(),
                    imageFrame.getWidth(), imageFrame.getHeight(), null);
    }

    @Override
    public void render(RectangleFrame rectangleFrame) {
        canvas.graphics.setColor(rectangleFrame.getColor());
        canvas.graphics.fillRect(rectangleFrame.getX(), rectangleFrame.getY(), rectangleFrame.getWidth(), rectangleFrame.getHeight());
    }
}
