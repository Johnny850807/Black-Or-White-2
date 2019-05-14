package com.pokewords.framework.sprites;

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
		//TODO
	}

}
