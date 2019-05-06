package com.pokewords.framework.views;

import java.awt.*;
import java.awt.geom.Point2D;

public class DefaultInputManager implements InputManager {


    @Override
    public void onUpdate(double timePerFrame) {

    }

    @Override
    public boolean getButtonPressedDown(int key) {
        return false;
    }

    @Override
    public void onButtonPressedDown(int key) {

    }

    @Override
    public boolean getButtonBeingHeld(int key) {
        return false;
    }

    @Override
    public void onButtonBeingHeld(int key) {

    }

    @Override
    public boolean getButtonReleasedUp(int key) {
        return false;
    }

    @Override
    public void onButtonReleasedUp(int key) {

    }

    @Override
    public Point2D getMousePosition() {
        return null;
    }

    @Override
    public void onMouseMoved(Point2D point) {

    }

    @Override
    public boolean getMouseHitDown() {
        return false;
    }

    @Override
    public void onMouseHitDown(Point position) {

    }

    @Override
    public boolean getMouseReleasedUp() {
        return false;
    }

    @Override
    public void onMouseReleasedUp() {

    }

    @Override
    public boolean getMouseBeingHeld() {
        return false;
    }

    @Override
    public void onMouseBeingHeld() {

    }
}
