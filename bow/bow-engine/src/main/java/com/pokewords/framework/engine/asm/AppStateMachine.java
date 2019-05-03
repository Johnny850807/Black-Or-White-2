package com.pokewords.framework.engine.asm;

import com.pokewords.framework.engine.FiniteStateMachine;
import com.pokewords.framework.engine.exceptions.GameEngineException;
import com.pokewords.framework.sprites.SpriteInitializer;
import com.pokewords.framework.sprites.components.GameLifecycleListener;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;
import com.pokewords.framework.views.GameWindowsConfigurator;
import com.pokewords.framework.views.InputManager;

/**
 * @author johnny850807 (waterball)
 */
public class AppStateMachine implements GameLifecycleListener {
	public static final String EVENT_LOADING = "Start Loading";
	public static final String EVENT_GAME_STARTED = "Game Started";
	private FiniteStateMachine<AppState> fsm = new FiniteStateMachine<>();
	private SpriteInitializer spriteInitializer;
	private GameWindowsConfigurator gameWindowsConfigurator;
	private InputManager inputManager;
	private AppState loadingState;
	private AppState gameInitialState;

	public AppStateMachine(InputManager inputManager, SpriteInitializer spriteInitializer, GameWindowsConfigurator gameWindowsConfigurator) {
		this.inputManager = inputManager;
		this.spriteInitializer = spriteInitializer;
		this.gameWindowsConfigurator = gameWindowsConfigurator;
		setupStates();
	}

	private void setupStates() {
		AppState initialState = createState(EmptyAppState.class);
		this.loadingState = createState(LoadingState.class);
		fsm.setCurrentState(initialState);
		fsm.addTransition(initialState, EVENT_LOADING, loadingState);
	}

	public <T extends AppState> T createState(Class<T> appStateType) {
		T state;
		try {
			state = appStateType.newInstance();
			state.inject(inputManager, this, spriteInitializer, gameWindowsConfigurator);
			fsm.addState(state);
			return state;
		} catch (InstantiationException|IllegalAccessException e) {
			throw new GameEngineException(String.format("Error occurs during createState(appStateType), " +
					"please make sure %s has an empty public constructor.", appStateType.getName()), e);
		}
	}

	public AppState trigger(String event) {
		AppState from = fsm.getCurrentState();
		AppState to = fsm.trigger(event);
		if (from != to)
		{
			from.onAppStateExit();
			if (!to.hasStarted())
				to.onAppStateStart(onCreateAppStateWorld());
			to.onAppStateEnter();
		}
		return to;
	}

	/**
	 * the hook method invoked whenever any AppState is started
	 * , this then requires initializing a new AppStateWorld for that AppState.
	 * For customizing your default init world, overwrite this method.
	 * @return the init app state world
	 */
	public AppStateWorld onCreateAppStateWorld(){
		return new AppStateWorld();
	}


	@Override
	public void onUpdate(double timePerFrame) {
		fsm.getCurrentState().onUpdate(timePerFrame);
	}

	public void setGameInitialState(AppState clientInitState) {
		this.gameInitialState = clientInitState;
	}

	public AppState getCurrentState() {
		return fsm.getCurrentState();
	}

	public AppStateWorld getCurrentStateWorld(){
		return getCurrentState().getAppStateWorld();
	}


	public void addTransition(AppState from, Object event, AppState to) {
		fsm.addTransition(from, event, to);
	}

	public void addTransitionFromAllStates(Object event, AppState targetState, AppState ...excepts) {
		fsm.addTransitionFromAllStates(event, targetState, excepts);
	}
}
