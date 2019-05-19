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
    private InputManager inputManager;

    public GamePanel(InputManager inputManager) {
        this.inputManager = inputManager;
        addKeyListener(new KeyListener());
        addMouseListener(new MouseListener());
    }

    private class KeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            inputManager.onButtonPressedDown(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            inputManager.onButtonReleasedUp(e.getKeyCode());
        }
    }


    private class MouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            inputManager.onMouseHitDown(e.getPoint());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            inputManager.onMouseReleasedUp(e.getPoint());
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            inputManager.onMouseMoved(e.getPoint());
        }
    }

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
