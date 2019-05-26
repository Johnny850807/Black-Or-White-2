package com.pokewords.framework.engine.asm;

import com.pokewords.framework.commons.FiniteStateMachine;
import com.pokewords.framework.commons.Triple;
import com.pokewords.framework.commons.bundles.Bundle;
import com.pokewords.framework.commons.bundles.EmptyReadOnlyBundle;
import com.pokewords.framework.commons.bundles.InputEventsDelegator;
import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.engine.asm.states.BreakerIconLoadingState;
import com.pokewords.framework.engine.asm.states.EmptyAppState;
import com.pokewords.framework.engine.exceptions.GameEngineException;
import com.pokewords.framework.ioc.IocContainer;
import com.pokewords.framework.engine.listeners.GameLoopingListener;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.views.SoundPlayer;
import com.pokewords.framework.views.effects.AppStateTransitionEffect;
import com.pokewords.framework.views.effects.AppStateTransitionEffectListenersWrapper;
import com.pokewords.framework.views.effects.CrossFadingTransitionEffect;
import com.pokewords.framework.views.effects.NoTransitionEffect;
import com.pokewords.framework.views.inputs.InputManager;

import java.util.HashMap;
import java.util.Map;

/**
 * The AppStateMachine manages the finite game states.
 *
 * Built-in transitions:
 * EmptyState --(EVENT_LOADING)--> LoadingState --(EVENT_GAME_STARTED)--> #gameInitialState (Set your gameInitialState)
 *
 * Use AppStateMachine#createState(appStateType) to fromGallery your app state.
 * @author johnny850807 (waterball)
 */
public class AppStateMachine implements GameLoopingListener {
	public static final String EVENT_LOADING = "Start Loading";
	public static final String EVENT_GAME_STARTED = "Game Started";

	private IocContainer iocContainer;
	private GameEngineFacade gameEngineFacade;

	private Map<Transition, AppStateTransitionEffect> transitionEffectMap = new HashMap<>();
	private boolean transitionEffecting = false;

	/**
	 * We should use this currentState reference instead of finite state machine's,
	 * because for handling the transition effect correctly,
	 * the currentState can only be updated after the transition effect is completed.
	 */
	private AppState currentState;
	private FiniteStateMachine<AppState> fsm = new FiniteStateMachine<>();
	private InputManager inputManager;
	private SoundPlayer soundPlayer;
	private AppState loadingState;
	private AppState gameInitialState;

	private enum SoundTypes {
		TRANSITION
	}

	public AppStateMachine(IocContainer iocContainer, GameEngineFacade gameEngineFacade) {
		this.iocContainer = iocContainer;
		this.inputManager = iocContainer.inputManager();
		this.soundPlayer = iocContainer.soundPlayer();
		this.gameEngineFacade = gameEngineFacade;
		soundPlayer.addSound(SoundTypes.TRANSITION, "assets/sounds/chimeTransitionSound.wav");
		InputEventsDelegator.delegateToInputEventsListenerComponents(inputManager, this::getCurrentStateWorld);
		setupStates();
	}

	private void setupStates() {
		AppState initialState = createState(EmptyAppState.class);
		this.currentState = initialState;
		this.loadingState = createState(BreakerIconLoadingState.class);
		fsm.setCurrentState(initialState);
		addTransition(initialState, EVENT_LOADING, loadingState);
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
			state.inject(iocContainer, this, gameEngineFacade);
			fsm.addState(state);
			state.onAppStateCreate();
			return state;
		} catch (InstantiationException|IllegalAccessException e) {
			throw new GameEngineException(String.format("Error occurs during createState(appStateType), " +
					"please make sure %s has an empty public constructor.", appStateType.getName()), e);
		}
	}

	public AppState trigger(Object event) {
		return trigger(event, EmptyReadOnlyBundle.getInstance());
	}

	public AppState trigger(Object event, Bundle message) {
		AppState from = currentState;
		AppState to = fsm.trigger(event.toString());
		if (from != to)
		{
			AppStateTransitionEffect transitionEffect =
					transitionEffectMap.getOrDefault(new Transition(from, event, to), NoTransitionEffect.getInstance());
			to.onReceiveMessageBundle(message);
			handleTransition(from, to, transitionEffect);
		}
		return to;
	}

	public AppState trigger(Object event, AppStateTransitionEffect transitionEffect, AppStateTransitionEffect.Listener ...listeners) {
		return trigger(event, EmptyReadOnlyBundle.getInstance(), transitionEffect, listeners);
	}

	public AppState trigger(Object event, Bundle message, AppStateTransitionEffect transitionEffect, AppStateTransitionEffect.Listener ...listeners) {
		AppState from = currentState;
		AppState to = fsm.trigger(event.toString());
		if (from != to)
		{
			to.onReceiveMessageBundle(message);
			handleTransition(from, to, new AppStateTransitionEffectListenersWrapper(transitionEffect, listeners));
		}
		return to;
	}

	private void handleTransition(AppState from, AppState to, AppStateTransitionEffect transitionEffect) {
		transitionEffecting = true;

		transitionEffect.effect(iocContainer.spriteBuilder(), from, to, new AppStateTransitionEffect.DefaultListener() {
			@Override
			public void onExitingAppStateEffectEnd() {
				from.onAppStateExit();
				currentState = to;
				to.onAppStateEnter();
				transitionEffecting = false;
			}
		});
	}


	@Override
	public void onUpdate(double timePerFrame) {
		if (transitionEffecting)
			getCurrentStateWorld().onUpdate(timePerFrame);
		else
			getCurrentState().onUpdate(timePerFrame);
	}


	public void setGameInitialState(AppState gameInitialState, AppStateTransitionEffect.Listener ...listeners) {
		setGameInitialState(gameInitialState, new CrossFadingTransitionEffect(SoundTypes.TRANSITION), listeners);
	}

	public void setGameInitialState(AppState gameInitialState, AppStateTransitionEffect transitionEffect, AppStateTransitionEffect.Listener ...listeners) {
		if (this.gameInitialState != null)
			throw new GameEngineException("You can only set the GameInitialState once.");
		this.gameInitialState = gameInitialState;
		addTransition(loadingState, EVENT_GAME_STARTED, this.gameInitialState,
				transitionEffect, listeners);
	}

	public AppState getCurrentState() {
		return currentState;
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

	public void addTransition(AppState from, Object event, AppState to, AppStateTransitionEffect transitionEffect
							, AppStateTransitionEffect.Listener ...listeners) {
		fsm.addTransition(from, event, to);
		transitionEffectMap.put(new Transition(from, event, to), new AppStateTransitionEffectListenersWrapper(transitionEffect, listeners));
	}


	public void addTransitionFromAllStates(Object event, AppState targetState, AppState ...excepts) {
		fsm.addTransitionFromAllStates(event, targetState, excepts);
	}

	private class Transition extends Triple<AppState, Object, AppState> {
		public Transition(AppState from, Object event, AppState to) {
			super(from, event, to);
		}
	}

}
