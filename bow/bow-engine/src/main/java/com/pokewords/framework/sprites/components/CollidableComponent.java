package com.pokewords.framework.sprites.components;

import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.components.marks.Shareable;

import java.awt.font.ImageGraphicAttribute;
import java.util.*;

/**
 * Mark a sprite as Collidable, you can add ignoredTypes into this component, those ignoredType sprites are not collided with
 * the sprite who owns this component.
 * @author johnny850807
 */
public class CollidableComponent extends Component implements Shareable {
	/**
	 * sprite's types that are ignored during collision detection.
	 */
	private Set<Object> ignoredTypes;

	public CollidableComponent() {
		this.ignoredTypes = new HashSet<>();
	}

	public static CollidableComponent ignoreTypes(Object ...ignoredTypes) {
		return new CollidableComponent(ignoredTypes);
	}

	protected CollidableComponent(Object ...ignoredTypes) {
		this.ignoredTypes = new HashSet<>(Arrays.asList(ignoredTypes));
	}

	public Set<Object> getIgnoredTypes() {
		return ignoredTypes;
	}

	public void addIgnoredType(Object type) {
		ignoredTypes.add(type);
	}

	public void removeIgnoredType(Object type) {
		ignoredTypes.remove(type);
	}

	/**
	 * @param type the sprite's type
	 * @return if the type is ignored during collision detection
	 */
	public boolean isIgnored(Object type) {
		return ignoredTypes.contains(type);
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CollidableComponent that = (CollidableComponent) o;
		return ignoredTypes.equals(that.ignoredTypes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ignoredTypes);
	}
	
}
