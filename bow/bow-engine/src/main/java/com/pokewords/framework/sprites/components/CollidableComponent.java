package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

/**
 * The singleton shareable CollidableComponent, its equals() method will always return true, and
 * its hashcode() will always return 0.
 * @author johnny850807
 */
public class CollidableComponent extends Component implements Shareable {
	private static CollidableComponent instance = new CollidableComponent();

	private CollidableComponent() {}

	public static CollidableComponent getInstance() {
		return instance;
	}

	@Override
	public void onAppStateStart(AppStateWorld world) {
	}

	@Override
	public void onUpdate(double tpf) { }

	@Override
	public void onAppStateEnter() { }

	@Override
	public void onAppStateExit() { }

    @Override
    public void onAppStateDestroy() { }

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(Object obj) {
		return true;
	}
}
