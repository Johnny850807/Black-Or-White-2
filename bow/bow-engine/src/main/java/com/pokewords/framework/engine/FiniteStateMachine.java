package com.pokewords.framework.engine;

import com.pokewords.framework.engine.exceptions.FiniteStateMachineException;

import java.util.*;

/**
 * @author Shawn
 */
public class FiniteStateMachine<T> implements Cloneable{

	/**
	 * Increase according to the amount of Nodes
	 */
	private int nodeNumber = 0;

	private ArrayList<T> stateList = new ArrayList<>();
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
	 * Show the overall status and currentState of the current 2D ArrayList.
	 * The first number of each line is the index of the first state of each line,
	 * e.g. 3|(0)122,(1)target,(2)z --> first state is '122' and it's index is 3.
	 *
	 * @return 2D ArrayList result
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Current State：").append(String.valueOf(currentState));
		result.append("\n\n");
		for(int i = 0; i < matrix.size(); i++){
			result.append(i).append("|");
			for(int j = 0; j < matrix.get(i).size(); j++){
				result.append("(").append(j).append(")");
				result.append(matrix.get(i).get(j).getState());
				result.append(",");
			}
			result.deleteCharAt(result.length()-1);
			result.append("\n");
		}
		System.out.println(result.toString());
		return result.toString();
	}


	/**
	 * The trigger function is able to switch different state
	 * @param event Trigger of event
	 * @return Return current state
	 */
	public T trigger(String event) {
		if(currentState == null){
			throw new FiniteStateMachineException("currentState not exists");
		}
		StateNode currentNode = stateNodesMap.get(currentState.hashCode());
		if(currentNode.getTargetNumberByCurrentNode(event) == null) {
			return currentState;
		}
		int startNumber = currentNode.getStateNumber();
		int targetNumber = currentNode.getTargetNumberByCurrentNode(event);
		for(int i = 0; i < matrix.get(startNumber).size(); i++){
			StateNode stateNode = matrix.get(startNumber).get(i);
			if(stateNode.getStateNumber() == targetNumber){
				currentState = matrix.get(startNumber).get(i).getState();
			}
		}
		return currentState;
	}

	/**
	 *
	 * @param currentState current state
	 * @throws FiniteStateMachineException
	 */
	public void setCurrentState(T currentState) throws FiniteStateMachineException {
		if(stateNodesMap.get(currentState.hashCode()) == null){
			throw new FiniteStateMachineException("Not exists");
		}else {
			StateNode stateNode = stateNodesMap.get(currentState.hashCode());
			this.currentState = stateNode.getState();
		}
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
		stateList.add(t);
		int stateNumber = nodeNumber++;
		StateNode newNode = new StateNode(t,stateNumber);
		stateNodesMap.put(t.hashCode(), newNode);
		ArrayList<StateNode> newArrayInList = new ArrayList<>();
		newArrayInList.add(newNode);
		matrix.add(newArrayInList);
	}

	/**
	 * @return all states
	 */
	public ArrayList<T> getStates(){
		return stateList;
	}

	/**
	 * Create a trigger transition with two different states
	 * @param from T that will be changed
	 * @param event the triggering event's name
	 * @param to is triggered state
	 */
	public void addTransition(T from, String event, T to) {
		if(stateNodesMap.get(from.hashCode()) == null || stateNodesMap.get(to.hashCode()) == null){
			throw new FiniteStateMachineException("Not exists");
		}else {
			StateNode fromNode = stateNodesMap.get(from.hashCode());
			StateNode toNode = stateNodesMap.get(to.hashCode());
			int fromNumber = fromNode.getStateNumber();
			int toNumber = toNode.getStateNumber();
			matrix.get(fromNumber).add(toNode);
			fromNode.setTriggerEvent(event, toNumber);
		}
	}

	/**
	 * Create a transition given an event name from each state (except targetState) to targetState.
	 * @param event the triggering event's name
	 * @param targetState the target state to transit to
	 */
	public void addTransitionFromAllStates(String event, T targetState, T ...excepts) {
		if(stateNodesMap.get(targetState.hashCode()) == null) {
			throw new FiniteStateMachineException("targetState not exists");
		}else {
			StateNode targetNode = stateNodesMap.get(targetState.hashCode());
			List<StateNode> exceptsNodeList = new ArrayList<>();
			for (T except : excepts) {
				StateNode exceptsNode = stateNodesMap.get(except.hashCode());
				exceptsNodeList.add(exceptsNode);
			}
			for (int i = 0; i < matrix.size(); i++) {
				if (i != targetNode.getStateNumber()) {
					matrix.get(i).add(targetNode);
				}
			}
			for (int i = 0; i < matrix.size(); i++) {
				for (StateNode exceptsListNode : exceptsNodeList) {
					if (i == exceptsListNode.getStateNumber()) {
						matrix.get(i).remove(targetNode);
					}
				}
			}
			for (ArrayList<StateNode> stateMatrix : matrix) {
				for (StateNode stateNodeMatrix : stateMatrix) {
					stateNodeMatrix.setTriggerEvent(event, targetNode.getStateNumber());
				}
			}
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

		private int stateNumber;

		private T state;

		/**
		 * triggerMap saves this corresponding node of event by this node
		 */
		private Map<String, Integer> triggerMap = new HashMap<String, Integer>();

		private StateNode(T state, int stateNumber) {
			this.state = state;
			this.stateNumber = stateNumber;
		}

		/**
		 * Set trigger event in this node
		 * @param event the triggering event's name
		 * @param targetNumber the target number
		 */
		public void setTriggerEvent(String event, int targetNumber){
			triggerMap.put(event, targetNumber);
		}

		/**
		 * Through event to obtain the target number by triggerMap in this node
		 * @param event the triggering event's name
		 * @return state number
		 */
		public Integer getTargetNumberByCurrentNode(String event){
			return triggerMap.get(event);
		}

		public int getStateNumber() {
			return stateNumber;
		}

		private T getState() {
			return state;
		}
	}


}