package com.pokewords.framework.engine.asm;

import com.pokewords.framework.engine.FiniteStateMachine;
import com.pokewords.framework.engine.exceptions.GameEngineException;
import com.pokewords.framework.sprites.SpriteInitializer;
import com.pokewords.framework.sprites.components.GameLifecycleListener;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;
import com.pokewords.framework.views.GameWindowDefinition;
import com.pokewords.framework.views.GameWindowsConfigurator;
import com.pokewords.framework.views.InputManager;

/**
 * @author johnny850807 (waterball)
 */
public class AppStateMachine implements GameLifecycleListener {
	private FiniteStateMachine<AppState> fsm = new FiniteStateMachine<>();
	private SpriteInitializer spriteInitializer;
	private GameWindowsConfigurator gameWindowsConfigurator;
	private InputManager inputManager;
	private AppState loadingState;
	private AppState gameInitialState;

	public AppStateMachine(InputManager inputManager, SpriteInitializer spriteInitializer, GameWindowsConfigurator gameWindowsConfigurator) {
		this.inputManager = inputManager;
		this.spriteInitializer = spriteInitializer;
		this.loadingState = createState(LoadingState.class);
		this.gameWindowsConfigurator = gameWindowsConfigurator;
		fsm.setCurrentState(loadingState);
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
		AppState enteredAppState = fsm.trigger(event);
		if (getCurrentState() != enteredAppState)
		{
			getCurrentState().onAppStateExit();
			if (!enteredAppState.hasStarted())
				enteredAppState.onAppStateStart(onCreateAppStateWorld());
			enteredAppState.onAppStateEnter();
		}
		return enteredAppState;
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
	public void onUpdate(double tpf) {
		fsm.getCurrentState().onUpdate(tpf);
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
