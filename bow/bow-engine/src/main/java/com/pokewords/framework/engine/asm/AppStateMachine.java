package com.pokewords.framework.engine.asm;

import com.pokewords.framework.commons.FiniteStateMachine;
import com.pokewords.framework.engine.exceptions.GameEngineException;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.engine.listeners.GameLifecycleListener;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.views.inputs.Inputs;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;

/**
 * @author johnny850807 (waterball)
 */
public class AppStateMachine implements GameLifecycleListener {
	public static final String EVENT_LOADING = "Start Loading";
	public static final String EVENT_GAME_STARTED = "Game Started";
	private FiniteStateMachine<AppState> fsm = new FiniteStateMachine<>();
	private SpriteInitializer spriteInitializer;
	private GameWindowsConfigurator gameWindowsConfigurator;
	private Inputs inputs;
	private AppState loadingState;
	private AppState gameInitialState;

	public AppStateMachine(Inputs inputs, SpriteInitializer spriteInitializer, GameWindowsConfigurator gameWindowsConfigurator) {
		this.inputs = inputs;
		this.spriteInitializer = spriteInitializer;
		this.gameWindowsConfigurator = gameWindowsConfigurator;
		setupStates();
	}

	private void setupStates() {
		AppState initialState = createState(EmptyAppState.class);
		this.loadingState = createState(LoadingState.class);
		fsm.setCurrentState(initialState);
		fsm.addTransition(initialState, EVENT_LOADING, loadingState);
		initialState.onAppStateCreate();
	}

	public <T extends AppState> T createState(Class<T> appStateType) {
		T state;
		try {
			state = appStateType.newInstance();
			state.inject(inputs, this, spriteInitializer, gameWindowsConfigurator);
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
				to.onAppStateCreate();
			to.onAppStateEnter();
		}
		return to;
	}


	@Override
	public void onUpdate(int timePerFrame) {
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
