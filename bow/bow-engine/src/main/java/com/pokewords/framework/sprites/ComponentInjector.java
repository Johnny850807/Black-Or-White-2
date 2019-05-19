package com.pokewords.framework.sprites;

import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.components.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author nyngwang
 * @deprecated
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
		for (Component component : sprite.getComponents()) {
			inject(sprite, component);
		}
	}

	public static void inject(Sprite sprite, Component component) {
		for (Field field : getInheritedPrivateFields(component.getClass())) {
			if (field.getType() == Sprite.class)
				injectField(field, component, sprite);
			else if (field.getType() == AppStateWorld.class)
				injectField(field, component, sprite.getWorld());
		}
	}

	private static List<Field> getInheritedPrivateFields(Class<?> type) {
		List<Field> result = new ArrayList<Field>();

		Class<?> i = type;
		while (i != null && i != Object.class) {
			for (Field field : i.getDeclaredFields()) {
				if (!field.isSynthetic()) {
					result.add(field);
				}
			}
			i = i.getSuperclass();
		}

		return result;
	}

	private static void injectField(Field field, Component component, Object injectedInstance) {
		try {
			boolean accessible = field.isAccessible();
			field.setAccessible(true);
			field.set(component, injectedInstance);
			field.setAccessible(accessible);
		} catch (IllegalAccessException | NullPointerException e) {
			e.printStackTrace();
		}
	}
}
