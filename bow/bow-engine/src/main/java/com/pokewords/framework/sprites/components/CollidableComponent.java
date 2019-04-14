package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

/**
 * @author johnny850807
 */
public class CollidableComponent implements Component {

	@Override
	public void onAppStateStart(AppStateWorld world) { }

	@Override
	public void onUpdate(double tpf) { }

	@Override
	public void onAppStateEnter() { }

	@Override
	public void onAppStateExit() { }

    @Override
    public void onAppStateDestroy() { }

	@Override
	public CollidableComponent clone() {
		try {
			return (CollidableComponent) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

}
