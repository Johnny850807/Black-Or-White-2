package com.pokewords.framework.views.windows;

import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.views.AppView;
import com.pokewords.framework.views.GraphicsCanvas;
import com.pokewords.framework.views.RenderedLayers;
import com.pokewords.framework.views.inputs.InputManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
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

    @Override
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
        drawRenderedLayers(g);
    }

    private void drawBackground(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawRenderedLayers(Graphics g) {
        List<List<Frame>> layers = renderedLayers.getLayers();
        for (int i = 0; i < layers.size(); i++) {
            List<Frame> layer = layers.get(i);
            for (int j = 0; j < layer.size(); j++) {
                layer.get(j).renderItself(GraphicsCanvas.of(g));
            }
        }
    }
}
