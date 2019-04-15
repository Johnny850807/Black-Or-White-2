package com.pokewords.framework.engine.asm;

import com.pokewords.framework.engine.FiniteStateMachine;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.components.GameLifecycleListener;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

/**
 * @author johnny850807
 */
public class AppStateMachine extends FiniteStateMachine<AppState> implements GameLifecycleListener {
	protected IocFactory iocFactory;
	protected AppState currentState;
	protected AppState loadingState;
	protected AppState clientInitState;


	public AppStateMachine(IocFactory iocFactory, AppState loadingState) {
		this.iocFactory = iocFactory;
		this.loadingState = loadingState;
		this.currentState = loadingState;
	}

//	public AppStateMachine(IocFactory iocFactory) {
//		this(iocFactory, new LoadingState(this));
//	}

	@Override
	public AppState trigger(String event) {
		AppState enteredAppState = super.trigger(event);
		if (currentState != enteredAppState)
		{
			currentState.onAppStateExit();
			if (!enteredAppState.isStarted())
				enteredAppState.onAppStateStart(onInitAppStateWorld());
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
	public AppStateWorld onInitAppStateWorld(){
		return new AppStateWorld();
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
		return currentState.getAppStateWorld();
	}

	public IocFactory getIocFactory() {
		return iocFactory;
	}
}
