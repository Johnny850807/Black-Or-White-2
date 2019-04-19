package com.pokewords.framework.views;

import com.pokewords.framework.views.InputManager;

import java.awt.geom.Point2D;

public class DefaultInputManager implements InputManager {

    @Override
    public boolean getButtonPressedDown(String key) {
        return false;
    }

    @Override
    public void onButtonPressedDown(String key) {

    }

    @Override
    public boolean getButtonBeingHeld(String key) {
        return false;
    }

    @Override
    public void onButtonBeingHeld(String key) {

    }

    @Override
    public boolean getButtonReleasedUp(String key) {
        return false;
    }

    @Override
    public void onButtonReleasedUp(String key) {

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
    public void onMouseHitDown() {

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
