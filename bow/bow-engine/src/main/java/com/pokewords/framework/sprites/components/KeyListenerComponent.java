package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;

public class KeyListenerComponent extends CloneableComponent {
    private Listener listener;

    public static KeyListenerComponent ofListener(Listener listener) {
        return new KeyListenerComponent(listener);
    }

    private KeyListenerComponent(Listener listener) {
        this.listener = listener;
    }


    @Override
    public void onUpdate(double timePerFrame) {
        listener.onUpdate(getOwnerSprite());
    }

    public abstract static class Listener implements Cloneable {
        public void onUpdate(Sprite sprite) {}
        public void onKeyPressed(Sprite sprite, int keyCode) {}
        public void onKeyReleased(Sprite sprite, int keyCode) {}
        public void onKeyClicked(Sprite sprite, int keyCode) {}

        public Listener clone() {
            try {
                return (Listener) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new Error(e);
            }
        }
    }

    public Listener getListener() {
        return listener;
    }

    @Override
    public KeyListenerComponent clone() {
        KeyListenerComponent clone = (KeyListenerComponent) super.clone();
        clone.listener = this.listener.clone();
        return clone;
    }
}
