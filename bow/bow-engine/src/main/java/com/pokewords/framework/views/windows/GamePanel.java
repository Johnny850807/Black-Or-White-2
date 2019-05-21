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
        setFocusable(true);
        addKeyListener(new GamePanel.KeyListener());
        addMouseListener(new GamePanel.MouseListener());
        addMouseMotionListener(new GamePanel.MouseListener());
    }

    public void setGamePanelBackground(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
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

        @Override
        public void mouseDragged(MouseEvent e) {
            inputManager.onMouseDragged(e.getPoint());
        }
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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        drawBackground(g2d);
        drawRenderedLayers(g2d);
    }

    private void drawBackground(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawRenderedLayers(Graphics g) {
        for (Frame frame : renderedLayers) {
            frame.renderItself(GraphicsCanvas.of(g));
        }
    }
}
