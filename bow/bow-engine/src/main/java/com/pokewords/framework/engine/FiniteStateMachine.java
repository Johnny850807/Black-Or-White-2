package com.pokewords.framework.engine;

import java.util.*;

public class FiniteStateMachine<T> {

	/**
	 * Increase according to the amount of Nodes
	 */
	private int nodeIndex = 0;

	/**
	 * Array save T
	 */
	private T[][] martix;

	private T currentState;

	/**
	 * stateNodesMap is to save new nodes
	 */
	private Map<Integer,StateNode> stateNodesMap = new HashMap<>();

	/**
	 * triggerMap saves each corresponding node of event by event
	 */
	private Map<String,Integer> triggerMap= new HashMap<>();

	/**
	 * The trigger function is able to switch different state
	 * @param event Trigger of event
	 * @return Return current state
	 */
	public T trigger(String event) {
		StateNode curentNode = stateNodesMap.get(currentState.hashCode());
		int startIndex = curentNode.getStateIndex();
		int targetIndex = triggerMap.get(event);
		currentState = martix[startIndex][targetIndex];
		return currentState;
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
		int stateNumber = nodeIndex++;
		StateNode newNode = new StateNode(t,stateNumber);
		stateNodesMap.put(t.hashCode(),newNode);
	}


	/**
	 * Create a trigger transition with two different states
	 * @param from T that will be changed
	 * @param event the triggering event's name
	 * @param to is triggered state
	 */
	public void addTransition(T from, String event, T to) {
		if(martix == null) {
			martix = (T[][]) new Object[nodeIndex][nodeIndex];
		}
		StateNode fromNode = stateNodesMap.get(from.hashCode());
		StateNode toNode = stateNodesMap.get(to.hashCode());

		int fromIndex = fromNode.getStateIndex();
		int toIndex = toNode.getStateIndex();
		martix[fromIndex][toIndex] = to;
		triggerMap.put(event, toIndex);
	}

	/**
	 * Create a transition given an event name from each state (except targetState) to targetState.
	 * @param event the triggering event's name
	 * @param targetState the target state to transit to
	 */
	public void addTransitionFromAllStates(String event, T targetState){
		StateNode targetNode = stateNodesMap.get(targetState.hashCode());
		for(int i=0; i<nodeIndex; i++){
			if(i == targetNode.getStateIndex())
				continue;
			martix[i][targetNode.getStateIndex()] = targetState;
		}
		triggerMap.put(event, targetNode.getStateIndex());
	}

	class StateNode {

		private int stateIndex;

		private T state;

		private StateNode(T state,int stateIndex) {
			this.state = state;
			this.stateIndex = stateIndex;
		}

		public int getStateIndex() {
			return stateIndex;
		}

		private T getState() {
			return state;
		}
	}

}