package com.pokewords.framework.engine.asm;

import com.pokewords.framework.engine.FiniteStateMachine;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.components.GameLifecycleListener;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

/**
 * @author johnny850807
 */
public abstract class AppStateMachine extends FiniteStateMachine<AppState> implements GameLifecycleListener {
	protected IocFactory iocFactory;
	protected AppState currentState;
	protected AppState loadingState;
	protected AppState clientInitState;


	public AppStateMachine(IocFactory iocFactory, AppState loadingState) {
		this.iocFactory = iocFactory;
		this.loadingState = loadingState;
		this.currentState = loadingState;
	}

	public AppStateMachine(IocFactory iocFactory) {
		this(iocFactory, new LoadingState());
	}

	@Override
	public AppState trigger(String event) {
		AppState enteringAppState = super.trigger(event);
		if (currentState != enteringAppState)
		{
			currentState.onAppStateExit();
			enteringAppState.onAppStateEnter();
		}
		return enteringAppState;
	}

	@Override
	public void onUpdate(double tpf) {
		getCurrentState().onUpdate(tpf);
	}

	public void setClientInitState(AppState clientInitState) {
		this.clientInitState = clientInitState;
	}

	public void setLoadingState(AppState loadingState) {
		this.loadingState = loadingState;
	}

	public AppStateWorld getCurrentStateWorld(){
		return currentState.getStateWorld();
	}

	public IocFactory getIocFactory() {
		return iocFactory;
	}
}
