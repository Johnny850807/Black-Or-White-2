package com.pokewords.framework.engine.asm;

import com.pokewords.framework.commons.FiniteStateMachine;
import com.pokewords.framework.engine.exceptions.GameEngineException;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.engine.listeners.GameLoopingListener;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.views.SoundPlayer;
import com.pokewords.framework.views.inputs.InputManager;
import com.pokewords.framework.views.inputs.Inputs;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;

/**
 * The AppStateMachine manages the finite game states.
 *
 * Built-in transitions:
 * EmptyState --(EVENT_LOADING)--> ProgressBarLoadingState --(EVENT_GAME_STARTED)--> #gameInitialState (Set your gameInitialState)
 *
 * Use AppStateMachine#createState(appStateType) to create your app state.
 * @author johnny850807 (waterball)
 */
public class AppStateMachine implements GameLoopingListener {
	public static final String EVENT_LOADING = "Start Loading";
	public static final String EVENT_GAME_STARTED = "Game Started";
	private FiniteStateMachine<AppState> fsm = new FiniteStateMachine<>();
	private SpriteInitializer spriteInitializer;
	private GameWindowsConfigurator gameWindowsConfigurator;
	private SoundPlayer soundPlayer;
	private InputManager inputManager;
	private AppState loadingState;
	private AppState gameInitialState;

	public AppStateMachine(InputManager inputManager, SpriteInitializer spriteInitializer, GameWindowsConfigurator gameWindowsConfigurator, SoundPlayer soundPlayer) {
		this.inputManager = inputManager;
		this.spriteInitializer = spriteInitializer;
		this.gameWindowsConfigurator = gameWindowsConfigurator;
		this.soundPlayer = soundPlayer;
		setupStates();
	}

	private void setupStates() {
		AppState initialState = createState(EmptyAppState.class);
		this.loadingState = createState(BreakerIconLoadingState.class);
		fsm.setCurrentState(initialState);
		fsm.addTransition(initialState, EVENT_LOADING, loadingState);
		initialState.onAppStateCreate();
	}

	/**
	 * Create the AppState of the given type, the AppStateMachine will add it into the machine.
	 * @param appStateType the app state's type
	 * @return the created AppState of the appStateType
	 */
	public <T extends AppState> T createState(Class<T> appStateType) {
		T state;
		try {
			state = appStateType.newInstance();
			state.inject(inputManager, this, spriteInitializer, gameWindowsConfigurator, soundPlayer);
			fsm.addState(state);
			state.onAppStateCreate();
			return state;
		} catch (InstantiationException|IllegalAccessException e) {
			throw new GameEngineException(String.format("Error occurs during createState(appStateType), " +
					"please make sure %s has an empty public constructor.", appStateType.getName()), e);
		}
	}

	public AppState trigger(Object event) {
		AppState from = fsm.getCurrentState();
		AppState to = fsm.trigger(event.toString());
		if (from != to)
		{
			from.onAppStateExit();
			to.onAppStateEnter();
		}
		return to;
	}


	@Override
	public void onUpdate(double timePerFrame) {
		fsm.getCurrentState().onUpdate(timePerFrame);
	}

	public void setGameInitialState(AppState clientInitState) {
		if (gameInitialState != null)
			throw new GameEngineException("You can only set the GameInitialState once.");
		this.gameInitialState = clientInitState;
		this.fsm.addState(gameInitialState);
		this.fsm.addTransition(loadingState, EVENT_GAME_STARTED, gameInitialState);
	}

	public AppState getCurrentState() {
		return fsm.getCurrentState();
	}

	public AppStateWorld getCurrentStateWorld(){
		return getCurrentState().getAppStateWorld();
	}

	public AppState getGameInitialState() {
		return gameInitialState;
	}

	public AppState getLoadingState() {
		return loadingState;
	}

	public void addTransition(AppState from, Object event, AppState to) {
		fsm.addTransition(from, event, to);
	}

	public void addTransitionFromAllStates(Object event, AppState targetState, AppState ...excepts) {
		fsm.addTransitionFromAllStates(event, targetState, excepts);
	}
}
