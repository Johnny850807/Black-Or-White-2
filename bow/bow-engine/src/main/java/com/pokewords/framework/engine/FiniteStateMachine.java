package com.pokewords.framework.engine;

import com.pokewords.framework.engine.asm.AppState;

public class FiniteStateMachine<T> {

	public T trigger(String event) {
		return null;
	}

	public T currentState() {
		return null;
	}

	public void addState(T t) {

	}

	public void addTransition(AppState from, String event, AppState to) {

	}

}
