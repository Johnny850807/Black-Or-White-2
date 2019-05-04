package com.pokewords.framework.engine.asm;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.SpriteInitializer;
import com.pokewords.framework.sprites.components.AppStateLifeCycleListener;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;
import com.pokewords.framework.views.GameWindowDefinition;
import com.pokewords.framework.views.GameWindowsConfigurator;
import com.pokewords.framework.views.InputManager;

import java.awt.*;

/**
 * @author johnny850807 (waterball)
 */
public abstract class AppState implements AppStateLifeCycleListener {
	private AppStateMachine asm;
	private SpriteInitializer spriteInitializer;
	private InputManager inputManager;
	private AppStateWorld appStateWorld;
	private boolean started = false;
	private GameWindowsConfigurator gameWindowsConfigurator;

	public AppState() { }

	/**
	 * this method is expected to be used by the AppStateMachine for initializing injection.
	 * @see AppStateMachine#createState(Class)
	 */
	protected void inject(InputManager inputManager, AppStateMachine asm, SpriteInitializer spriteInitializer, GameWindowsConfigurator gameWindowsConfigurator) {
		this.asm = asm;
		this.spriteInitializer = spriteInitializer;
		this.inputManager = inputManager;
		this.gameWindowsConfigurator = gameWindowsConfigurator;
	}

	@Override
	public void onAppStateStart(AppStateWorld world) {
		this.started = true;
		this.appStateWorld = world;
		this.appStateWorld.onAppStateStart(world);
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

	public InputManager getInputManager() {
		return inputManager;
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
