package com.pokewords.framework.sprites;

import com.pokewords.framework.sprites.components.Component;

import java.lang.reflect.Field;

/**
 * @author nyngwang
 */
public class ComponentInjector {
	/**
	 * - Inject the sprite instance into its all components
	 * - Inject the sprite instance into its all Renderable Component's frames (access those frames by invoking `#Renderable.getAllFrames()` (edited) 
	 *
	 * Inject the Sprite's components via reflection,
	 * this method will locate each field of each component,
	 * if a field is of Sprite type, then inject that sprite instance in.
	 * @param sprite the injected Sprite
	 */
	public static void inject(Sprite sprite) {
		try {
			for (Component component : sprite.getComponents()) {
				Field field = component.getClass().getDeclaredField("sprite");
				field.setAccessible(true);
				field.set(component, sprite);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
