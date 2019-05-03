package com.pokewords.framework.views;

import com.pokewords.framework.sprites.components.Frame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author johnny850807 (waterball)
 */
public class GamePanel extends JPanel implements AppView {
    private Color backgroundColor;
    private RenderedLayers renderedLayers = new RenderedLayers();

    public void setGamePanelBackground(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void onAppInit() {

    }

    @Override
    public void onAppLoading() {

    }

    @Override
    public void onAppStarted() {

    }

    @Override
    public void onRender(RenderedLayers renderedLayers) {
        this.renderedLayers = renderedLayers;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawBackground(g);
        List<List<Frame>> layers = renderedLayers.layers;
        for (List<com.pokewords.framework.sprites.components.Frame> layer : layers) {
            for (Frame frames : layer) {
                frames.renderItself(GraphicsCanvas.of(g));
            }
        }
    }

    private void drawBackground(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
