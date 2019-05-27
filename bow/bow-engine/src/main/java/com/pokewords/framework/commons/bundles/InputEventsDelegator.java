package com.pokewords.framework.commons.bundles;

import com.pokewords.framework.commons.tools.QuaternaryConsumer;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.MouseListenerComponent;
import com.pokewords.framework.views.inputs.InputManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.function.Supplier;

/**
 * The static class dedicated in delegating the input events from certain input-manager to certain object.
 * Because the object might have different input listening interface, so we do adapting in this class.
 * @author johnny850807 (waterball)
 */
public class InputEventsDelegator {

    /**
     * @param inputManager the inputManager's instance
     * @param currentWorldSupplier factory returning the current AppStateWorld
     */
    public static void delegateToInputEventsListenerComponents(InputManager inputManager, Supplier<AppStateWorld> currentWorldSupplier) {
        inputManager.bindKeyEventForRoot(KeyEvent.KEY_PRESSED, keyCode ->
                currentWorldSupplier.get().getKeyListenerComponents()
                        .forEach(c -> c.getListener().onKeyPressed(c.getOwnerSprite(), keyCode)));

        inputManager.bindKeyEventForRoot(KeyEvent.KEY_RELEASED, keyCode ->
                currentWorldSupplier.get().getKeyListenerComponents()
                        .forEach(c -> c.getListener().onKeyReleased(c.getOwnerSprite(), keyCode)));

        inputManager.bindKeyEventForRoot(KeyEvent.KEY_TYPED, keyCode ->
                currentWorldSupplier.get().getKeyListenerComponents()
                        .forEach(c -> c.getListener().onKeyClicked(c.getOwnerSprite(), keyCode)));

        inputManager.bindMouseEventForRoot(MouseEvent.MOUSE_MOVED, mousePosition ->
                currentWorldSupplier.get().getMouseListenerComponents()
                        .forEach(c -> {
                            Sprite sprite = c.getOwnerSprite();
                            if (!c.isMouseEntered() && sprite.getArea().contains(mousePosition))
                            {
                                Point positionInArea = new Point((int) mousePosition.getX() - sprite.getX(), (int) mousePosition.getY() - sprite.getY());
                                c.getListener().onMouseEnter(sprite, mousePosition, positionInArea);
                            }
                            else if (c.isMouseEntered() && !sprite.getArea().contains(mousePosition))
                                c.getListener().onMouseExit(sprite, mousePosition);

                        })
        );

        inputManager.bindMouseEventForRoot(MouseEvent.MOUSE_PRESSED, mousePosition ->
                fireMouseListenerComponentsIfMousePositionContained(mousePosition, currentWorldSupplier,
                        MouseListenerComponent.Listener::onMousePressed));

        inputManager.bindMouseEventForRoot(MouseEvent.MOUSE_RELEASED, mousePosition ->
                fireMouseListenerComponentsIfMousePositionContained(mousePosition, currentWorldSupplier,
                        MouseListenerComponent.Listener::onMouseReleased));

        inputManager.bindMouseEventForRoot(MouseEvent.MOUSE_CLICKED, mousePosition ->
                fireMouseListenerComponentsIfMousePositionContained(mousePosition, currentWorldSupplier,
                        MouseListenerComponent.Listener::onMouseClicked));
    }

    private static void fireMouseListenerComponentsIfMousePositionContained(Point mousePosition, Supplier<AppStateWorld> currentWorldSupplier,
                                                                     QuaternaryConsumer<MouseListenerComponent.Listener, Sprite, Point, Point> firing)
    {
        currentWorldSupplier.get().getMouseListenerComponents().stream()
                .filter(c -> c.getOwnerSprite().getArea().contains(mousePosition))
                .forEach(c -> {
                    Sprite sprite = c.getOwnerSprite();
                    Point positionInArea = new Point((int) mousePosition.getX() - sprite.getX(), (int) mousePosition.getY() - sprite.getY());
                    firing.accept(c.getListener(), sprite, mousePosition, positionInArea);
                });
    }

}