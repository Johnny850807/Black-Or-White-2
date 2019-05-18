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
	public void onAppStateCreate() {
		this.appStateWorld = onCreateAppStateWorld();
		onAppStateCreating(appStateWorld);
		this.appStateWorld.onAppStateCreate();
	}

	protected abstract void onAppStateCreating(AppStateWorld appStateWorld);

	/**
	 * the hook method invoked whenever any AppState is created
	 * , this then requires initializing a new AppStateWorld for that AppState.
	 * For customizing your AppStateWorld, overwrite this method.
	 * @return the created app state world
	 */
	protected AppStateWorld onCreateAppStateWorld() {
		return new AppStateWorld();
	}

	@Override
	public final void onAppStateEnter() {
		onAppStateEntering();
		appStateWorld.onAppStateEnter();
	}

	protected abstract void onAppStateEntering();

	@Override
	public final void onAppStateExit() {
		onAppStateExiting();
		appStateWorld.onAppStateExit();
	}

	protected abstract void onAppStateExiting();

	@Override
	public final void onAppStateDestroy() {
		onAppStateDestroying();
		appStateWorld.onAppStateDestroy();
	}

	protected abstract void onAppStateDestroying();

	@Override
	public final void onUpdate(double timePerFrame) {
		onAppStateUpdating(timePerFrame);
		appStateWorld.onUpdate(timePerFrame);
	}

	protected abstract void onAppStateUpdating(double timePerFrame);

	protected Sprite createSprite(Object type) {
		return spriteInitializer.createSprite(type);
	}

	public SpriteInitializer getSpriteInitializer() {
		return spriteInitializer;
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
