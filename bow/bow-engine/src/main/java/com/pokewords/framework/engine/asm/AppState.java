package com.pokewords.framework.engine.asm;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.engine.listeners.AppStateLifeCycleListener;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.views.inputs.Inputs;
import com.pokewords.framework.views.windows.GameWindowDefinition;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;

import java.awt.*;

/**
 * @author johnny850807 (waterball)
 */
public abstract class AppState implements AppStateLifeCycleListener {
	private AppStateMachine asm;
	private SpriteInitializer spriteInitializer;
	private Inputs inputs;
	private AppStateWorld appStateWorld;
	private boolean started = false;
	private GameWindowsConfigurator gameWindowsConfigurator;

	public AppState() { }

	/**
	 * this method is expected to be used by the AppStateMachine for initializing injection.
	 * @see AppStateMachine#createState(Class)
	 */
	protected void inject(Inputs inputs, AppStateMachine asm, SpriteInitializer spriteInitializer, GameWindowsConfigurator gameWindowsConfigurator) {
		this.asm = asm;
		this.spriteInitializer = spriteInitializer;
		this.inputs = inputs;
		this.gameWindowsConfigurator = gameWindowsConfigurator;
	}

	@Override
	public void onAppStateCreate(AppStateWorld world) {
		this.started = true;
		this.appStateWorld = world;
		this.appStateWorld.onAppStateCreate(world);
	}

	protected Sprite createSprite(Object type) {
		return spriteInitializer.createSprite(type);
	}

	public SpriteInitializer getSpriteInitializer() {
		return spriteInitializer;
	}

	public boolean hasStarted() {
		return started;
	}

	public Inputs getInputs() {
		return inputs;
	}

	public AppStateMachine getAppStateMachine() {
		return asm;
	}

	public AppStateWorld getAppStateWorld() {
		return appStateWorld;
	}

	public Point getWindowSize() {
		return gameWindowsConfigurator.getGameWindowDefinition().size;
	}

	public GameWindowsConfigurator getGameWindowsConfigurator() {
		return gameWindowsConfigurator;
	}

	public GameWindowDefinition getGameWindowDefinition() {
		return gameWindowsConfigurator.getGameWindowDefinition();
	}
}
