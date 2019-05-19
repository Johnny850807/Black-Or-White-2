package com.pokewords.framework.engine.asm;

import com.pokewords.framework.commons.FiniteStateMachine;
import com.pokewords.framework.commons.Triple;
import com.pokewords.framework.engine.exceptions.GameEngineException;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.engine.listeners.GameLoopingListener;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.views.SoundPlayer;
import com.pokewords.framework.views.effects.AppStateTransitionEffect;
import com.pokewords.framework.views.effects.CrossFadingTransitionEffect;
import com.pokewords.framework.views.effects.NoTransitionEffect;
import com.pokewords.framework.views.inputs.InputManager;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;

import java.util.HashMap;
import java.util.Map;

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

	private Map<Transition, TransitionEffectWrapper> transitionEffectMap = new HashMap<>();

	/**
	 * We should use this currentState reference instead of finite state machine's,
	 * because for handling the transition effect correctly,
	 * the currentState can only be updated after the transition effect is completed.
	 */
	private AppState currentState;
	private FiniteStateMachine<AppState> fsm = new FiniteStateMachine<>();
	private SpriteInitializer spriteInitializer;
	private GameWindowsConfigurator gameWindowsConfigurator;
	private SoundPlayer soundPlayer;
	private InputManager inputManager;
	private AppState loadingState;
	private AppState gameInitialState;

	private enum SoundTypes {
		TRANSITION
	}

	public AppStateMachine(InputManager inputManager, SpriteInitializer spriteInitializer, GameWindowsConfigurator gameWindowsConfigurator, SoundPlayer soundPlayer) {
		this.inputManager = inputManager;
		this.spriteInitializer = spriteInitializer;
		this.gameWindowsConfigurator = gameWindowsConfigurator;
		this.soundPlayer = soundPlayer;
		soundPlayer.addSound(SoundTypes.TRANSITION, "assets/sounds/chimeTransitionSound.wav");
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
			handleTransition(from, event, to);
		return to;
	}

	private void handleTransition(AppState from, Object event, AppState to) {
		Transition transition = new Transition(from, event, to);

		TransitionEffectWrapper transitionEffectWrapper = transitionEffectMap.getOrDefault(transition,
				new TransitionEffectWrapper(new NoTransitionEffect()));

		transitionEffectWrapper.effect(from, to, new AppStateTransitionEffect.DefaultListener() {
			@Override
			public void onFromEffectEnd() {
				from.onAppStateExit();
				currentState = to;
				to.onAppStateEnter();
			}
		});
	}


	@Override
	public void onUpdate(double timePerFrame) {
		getCurrentState().onUpdate(timePerFrame);
	}

	public void setGameInitialState(AppState gameInitialState, AppStateTransitionEffect.Listener ...listeners) {
		if (this.gameInitialState != null)
			throw new GameEngineException("You can only set the GameInitialState once.");
		this.gameInitialState = gameInitialState;
		this.fsm.addState(this.gameInitialState);
		addTransition(loadingState, EVENT_GAME_STARTED, this.gameInitialState,
				new CrossFadingTransitionEffect(SoundTypes.TRANSITION), listeners);
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
		transitionEffectMap.put(new Transition(from, event, to), new TransitionEffectWrapper(transitionEffect, listeners));
	}


	public void addTransitionFromAllStates(Object event, AppState targetState, AppState ...excepts) {
		fsm.addTransitionFromAllStates(event, targetState, excepts);
	}

	/**
	 * <from state, event type, to state>
	 */
	private class Transition extends Triple<AppState, Object, AppState> {
		public Transition(AppState from, Object event, AppState to) {
			super(from, event, to);
		}
	}

	private class TransitionEffectWrapper {
		private AppStateTransitionEffect appStateTransitionEffect;
		private AppStateTransitionEffect.Listener[] listeners;

		public TransitionEffectWrapper(AppStateTransitionEffect appStateTransitionEffect,
									   AppStateTransitionEffect.Listener ...listeners) {
			this.appStateTransitionEffect = appStateTransitionEffect;
			this.listeners = listeners;
		}

		public void effect(AppState from, AppState to, AppStateTransitionEffect.Listener transitionEffectListener) {
			appStateTransitionEffect.effect(from, to, new AppStateTransitionEffect.Listener() {
				@Override
				public void onFromEffectEnd() {
					for (AppStateTransitionEffect.Listener listener : listeners) {
						listener.onFromEffectEnd();
					}
					transitionEffectListener.onFromEffectEnd();
				}

				@Override
				public void onToEffectEnd() {
					for (AppStateTransitionEffect.Listener listener : listeners) {
						listener.onToEffectEnd();
					}
					transitionEffectListener.onToEffectEnd();
				}
			});
		}
	}
}
