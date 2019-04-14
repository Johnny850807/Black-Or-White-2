package com.pokewords.framework.engine;

import com.pokewords.framework.engine.asm.AppState;

import java.util.*;

public class FiniteStateMachine<T> {

	/**
	 * Current state
	 */
	private T currentState;

	/**
	 * stateNodesMap is to save new nodes
	 */
	private Map<Integer,StateNode> stateNodesMap = new HashMap<Integer, StateNode>();

	/**
	 * triggerMap saves each corresponding node of event by event
	 */
	private Map<String,StateNode> triggerMap = new HashMap<String,StateNode>();


	/**
	 * The trigger function is able to switch different state
	 * @param event: Trigger of event
	 * @return Return previous state, returning void is also Okay,
	 */
	public T trigger(String event) {
		StateNode from = triggerMap.get(event);
		currentState = from.getTransitionState(event);
		return from.getState();
	}

	/**
	 * Return current state
	 * @return current state
	 */
	public T getCurrentState() {
		return currentState;
	}

	/**
	 * Add a new state to stateNodesMap
	 * @param t element to be added to this Map
	 */
	public void addState(T t) {
		StateNode newNode = new StateNode(t);
		stateNodesMap.put(newNode.hashCode(),newNode);
	}

	/**
	 * Create a trigger transition with two different states
	 * @param from AppState that will be changed
	 * @param event the triggering event's name
	 * @param to is triggered state
	 */
	public void addTransition(AppState from, String event, AppState to) {
		StateNode fromState = stateNodesMap.get(from.hashCode());
		StateNode toState = stateNodesMap.get(to.hashCode());
		fromState.addTransition(event, toState.getState());
		triggerMap.put(event,fromState);
	}

	/**
	 * Create a transition given an event name from each state (except targetState) to targetState.
	 * @param event the triggering event's name
	 * @param targetState the target state to transit to
	 */
	public void addTransitionFromAllStates(String event, AppState targetState){
		//TODO
	}

	/**
	 * Transition graph node
	 */
	private class StateNode {

		/**
		 * transitionMap is to save each trigger event of this node
		 */
		private Map<String,T> transitionMap = new HashMap<String, T>();

		/**
		 * state of this node
		 */
		private T state;

		private StateNode(T state) {
			this.state = state;
		}

		/**
		 * Create a trigger transition with this object and param t
		 * @param event Trigger of event
		 * @param t element to be added to this Map
		 */
		private void addTransition(String event,T t){
			transitionMap.put(event,t);
		}

		/**
		 *
		 * @param event Trigger of event
		 * @return the triggered state
		 */
		private T getTransitionState(String event){
			return transitionMap.get(event);
		}

		private T getState() {
			return state;
		}
	}

}
