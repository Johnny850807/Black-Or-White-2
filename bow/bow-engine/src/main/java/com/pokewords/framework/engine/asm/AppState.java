package com.pokewords.framework.engine.asm;

import com.pokewords.framework.commons.bundles.Bundle;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.engine.listeners.AppStateLifeCycleListener;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.views.SoundPlayer;
import com.pokewords.framework.views.inputs.InputManager;
import com.pokewords.framework.views.windows.GameWindowDefinition;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

/**
 * @author johnny850807 (waterball)
 */
public abstract class AppState implements AppStateLifeCycleListener {
	private AppStateMachine asm;
	private SpriteInitializer spriteInitializer;
	private InputManager inputManager;
	private AppStateWorld appStateWorld;
	private GameWindowsConfigurator gameWindowsConfigurator;
	private SoundPlayer soundPlayer;

	public AppState() { }

	/**
	 * this method is expected to be used by the AppStateMachine for initializing injection.
	 * @see AppStateMachine#createState(Class)
	 */
	protected void inject(InputManager inputManager, AppStateMachine asm, SpriteInitializer spriteInitializer,
						  GameWindowsConfigurator gameWindowsConfigurator, SoundPlayer soundPlayer) {
		this.asm = asm;
		this.spriteInitializer = spriteInitializer;
		this.inputManager = inputManager;
		this.gameWindowsConfigurator = gameWindowsConfigurator;
		this.soundPlayer = soundPlayer;
	}

	@Override
	public void onAppStateCreate() {
		this.appStateWorld = onCreateAppStateWorld();
		onAppStateCreating(appStateWorld);
		this.appStateWorld.onAppStateCreate();
	}

	protected abstract void onAppStateCreating(AppStateWorld appStateWorld);

	/**
	 * bind a listener to the key pressed down event
	 * @param listener key listener
	 */
	protected void bindKeyPressedAction(Consumer<Integer> listener) {
		inputManager.bindKeyEvent(this, KeyEvent.KEY_PRESSED, listener);
	}

	/**
	 * bind a listener to the key released up event
	 * @param listener key listener
	 */
	protected void bindKeyReleasedAction(Consumer<Integer> listener) {
		inputManager.bindKeyEvent(this, KeyEvent.KEY_RELEASED, listener);
	}

	/**
	 * bind a listener to the key clicked event, the key clicked event will occur when
	 * a key pressed event followed by a key released event of the same keyCode.
	 * @param listener key listener
	 */
	protected void bindKeyClickedAction(Consumer<Integer> listener) {
		inputManager.bindKeyEvent(this, KeyEvent.KEY_TYPED, listener);
	}

	/**
	 * bind a listener (consumes the mouse position) to the mouse clicked event
	 * @param listener mouse listener
	 */
	protected void bindMouseClickedAction(Consumer<Point> listener) {
		inputManager.bindMouseEvent(this, MouseEvent.MOUSE_CLICKED, listener);
	}

	/**
	 * bind a listener (consumes the mouse position) to the mouse dragged event
	 * @param listener mouse listener
	 */
	protected void bindMouseDraggedAction(Consumer<Point> listener) {
		inputManager.bindMouseEvent(this, MouseEvent.MOUSE_DRAGGED, listener);
	}

	/**
	 * bind a listener (consumes the mouse position) to the mouse pressed down event
	 * @param listener mouse listener
	 */
	protected void bindMousePressedAction(Consumer<Point> listener) {
		inputManager.bindMouseEvent(this, MouseEvent.MOUSE_PRESSED, listener);
	}

	/**
	 * bind a listener (consumes the mouse position) to the mouse released up event
	 * @param listener mouse listener
	 */
	protected void bindMouseReleasedAction(Consumer<Point> listener) {
		inputManager.bindMouseEvent(this, MouseEvent.MOUSE_RELEASED, listener);
	}

	/**
	 * bind a listener (consumes the mouse position) to the mouse clicked event,
	 * the mouse clicked event will occur when a mouse pressed event followed by a mouse released
	 * event.
	 * @param listener mouse listener
	 */
	protected void bindMouseMovedAction(Consumer<Point> listener) {
		inputManager.bindMouseEvent(this, MouseEvent.MOUSE_MOVED, listener);
	}

	/**
	 * the hook method invoked whenever any AppState is created
	 * , this then requires initializing a new AppStateWorld for that AppState.
	 * For customizing your AppStateWorld, overwrite this method.
	 * @return the created app state world
	 */
	protected AppStateWorld onCreateAppStateWorld() {
		return new AppStateWorld(this, getSpriteInitializer());
	}

	/**
	 * This method will be triggered before #onAppStateEntering.
	 * If there is no bundle for the transition, the EmptyReadOnlyBundle will be passed in.
	 * In the normal situation, you should not operate on the EmptyReadOnlyBundle,
	 * hence only override this method only if you know there will be certain bundle.
	 * @param bundle
	 */
	public void onReceiveMessageBundle(Bundle bundle) {
		// hook
	}

	@Override
	public final void onAppStateEnter() {
		if (isListeningToInputEvents())
			inputManager.bindAppState(this);
		onAppStateEntering();
		appStateWorld.onAppStateEnter();
	}

	protected abstract void onAppStateEntering();

	@Override
	public final void onAppStateExit() {
		inputManager.unbind();
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

	/**
	 * a hook method, overwrite this method to return false if an AppState is not
	 * interested in any Input events to increase performance.
	 */
	protected boolean isListeningToInputEvents() {
		return true;
	}

	protected abstract void onAppStateUpdating(double timePerFrame);

	protected Sprite createSprite(Object type) {
		return spriteInitializer.createSprite(type);
	}

	public SpriteInitializer getSpriteInitializer() {
		return spriteInitializer;
	}

	public AppStateMachine getAppStateMachine() {
		return asm;
	}

	public AppStateWorld getAppStateWorld() {
		return appStateWorld;
	}

	public Dimension getWindowSize() {
		return gameWindowsConfigurator.getGameWindowDefinition().size;
	}

	public GameWindowsConfigurator getGameWindowsConfigurator() {
		return gameWindowsConfigurator;
	}

	public GameWindowDefinition getGameWindowDefinition() {
		return gameWindowsConfigurator.getGameWindowDefinition();
	}

	public SoundPlayer getSoundPlayer() {
		return soundPlayer;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
