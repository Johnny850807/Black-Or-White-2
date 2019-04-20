package com.pokewords.framework.engine;

import jdk.nashorn.internal.runtime.regexp.joni.ast.StateNode;

import java.util.*;

/**
 * @author Shawn
 */
public class FiniteStateMachine<T> implements Cloneable{

	/**
	 * Increase according to the amount of Nodes
	 */
	private int nodeIndex = 0;

	/**
	 * ArrayList save StateNode
	 */
	private ArrayList<ArrayList<StateNode>> matrix = new ArrayList<>();

	private T currentState;

	/**
	 * stateNodesMap is to save new nodes
	 */
	private Map<Integer, StateNode> stateNodesMap = new HashMap<Integer, StateNode>();

	/**
	 * triggerMap saves each corresponding node of event by event
	 */
	private Map<String, Integer> triggerMap = new HashMap<String, Integer>();

	@Override
	public String toString() {
		return matrix.toString();
	}

	/**
	 * The trigger function is able to switch different state
	 * @param event Trigger of event
	 * @return Return current state
	 */
	public T trigger(String event) {
		if(triggerMap.get(event) == null) {
			return currentState;
		}
		StateNode currentNode = stateNodesMap.get(currentState.hashCode());
		int startIndex = currentNode.getStateIndex();
		int targetIndex = triggerMap.get(event);
		for(int i = 0; i < matrix.get(startIndex).size(); i++){
			StateNode stateNode = matrix.get(startIndex).get(i);
			if(stateNode.getStateIndex() == targetIndex){
				currentState = matrix.get(startIndex).get(i).getState();
			}
		}
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
		ArrayList<StateNode> newArrayInList = new ArrayList<>();
		newArrayInList.add(newNode);
		matrix.add(newArrayInList);
	}


	/**
	 * Create a trigger transition with two different states
	 * @param from T that will be changed
	 * @param event the triggering event's name
	 * @param to is triggered state
	 */
	public void addTransition(T from, String event, T to) {
		StateNode fromNode = stateNodesMap.get(from.hashCode());
		StateNode toNode = stateNodesMap.get(to.hashCode());
		int fromIndex = fromNode.getStateIndex();
		int toIndex = toNode.getStateIndex();
		matrix.get(fromIndex).add(toNode);
		triggerMap.put(event, toIndex);
	}

	/**
	 * Create a transition given an event name from each state (except targetState) to targetState.
	 * @param event the triggering event's name
	 * @param targetState the target state to transit to
	 */
	public void addTransitionFromAllStates(String event, T targetState, T ...excepts){
		StateNode targetNode = stateNodesMap.get(targetState.hashCode());
		List<StateNode> exceptsNodeList = new ArrayList<>();
		for (T except : excepts) {
			StateNode exceptsNode = stateNodesMap.get(except.hashCode());
			exceptsNodeList.add(exceptsNode);
		}
		for(int i = 0; i < matrix.size(); i++){
			if(i != targetNode.getStateIndex()) {
				if(i != exceptsNodeList.get(i).getStateIndex()) {
					matrix.get(i).add(targetNode);
				}
			}
			triggerMap.put(event, targetNode.getStateIndex());
		}
	}

	/**
	 * @return the shallow copied FSM
	 */
	public FiniteStateMachine clone(){
		try {
			return (FiniteStateMachine) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}

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


	public static void main(String[] args) {
		FiniteStateMachine<String> finiteStateMachine = new FiniteStateMachine<>();
		finiteStateMachine.addState("A");
		finiteStateMachine.addState("B");
		finiteStateMachine.addState("C");
		finiteStateMachine.addTransition("A", "1", "B");
		finiteStateMachine.addTransition("B", "2", "C");
		finiteStateMachine.addTransition("C", "3", "A");
		System.out.println(finiteStateMachine.toString());
	}
}