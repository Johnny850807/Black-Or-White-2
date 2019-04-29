package com.pokewords.framework.engine;

import com.pokewords.framework.engine.exceptions.FiniteStateMachineException;

import java.util.*;

/**
 * @author Shawn
 */
public class FiniteStateMachine<T> implements Cloneable{
	/**
	 * Point to the current state
	 */
	private T currentState;

	/**
	 * Index of each event.
	 * Give each event an index to operate in matrix
	 */
	private int eventIndex = 0;

	/**
	 * Use index to save event.
	 */
	private Map<String, Integer> eventMap = new HashMap<>();

	/**
	 * Save each state in the List, its index is the index of the state
	 */
	private List<T> stateList = new ArrayList<>();

	/**
	 * matrix for storing events and states
	 *
	 * If current state is A, its index is 0.
	 * The index value of WakeUp event is 25,
	 * and the state triggered by the WakeUp event is B.
	 *
	 * Then B will be stored like this:
	 * matrix.get(0).get(25)
	 */
	private ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();

	/**
	 * Save each state,key is hashcode of state.
	 */
	private Map<Integer, StateNode> stateNodesMap = new HashMap<>();


	/**
	 * Show the overall status and currentState of the current matrix.
	 * The first number of each line is the index of the first state of each line,

	 * e.g. 3|(0)122,(1)target,(2)z --> first state is '122' and it's index is 3.
	 *
	 * If the other states are the same (pointing to itself), use "-" instead
	 *
	 * e.g. originally might be like this:
	 *  2|(0)2,(1)2,(2)2,(3)1,(4)2,(5)2
	 *  become to ->
	 *  2|(0)2,-,-,(3)1,-,-
	 *
	 * @return Complete matrix string
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Current State：").append(currentState);
		result.append("\n\n");
		for(int i = 0; i < matrix.size(); i++)
		{
			result.append(i).append("|");
			int initStateIndex = matrix.get(i).get(0);
			for(int j = 0; j < matrix.get(i).size(); j++)
			{
				int stateIndex = matrix.get(i).get(j);
				if(stateIndex == initStateIndex && j != 0){
					result.append("-");
				}else {
					result.append("(").append(j).append(")");
					result.append(stateIndex);
				}
				result.append(",");
			}
			result.deleteCharAt(result.length()-1);
			result.append("\n");
		}
		return result.toString();
	}

	/**
	 *  through the event to trigger the corresponding state, so that currentState points to the triggered state.
	 *  If the event does not exist, do nothing and currentState is returned.
	 *e.g.
	 *   fsm.addState(A);
	 *   fsm.addState(B);
	 *   fsm.addState(C);
	 *   fsm.addState(D);
	 *   fsm.addState(E);
	 *   fsm.addState(F);
	 *   fsm.addState(G);
	 *   fsm.addState(H);
	 *
	 *   fsm.addTransition(A, WakeUp, H);
	 *   fsm.addTransition(B, GO, H);
	 *   fsm.addTransition(D, Sleep, C);
	 *   fsm.addTransition(B, WakeUp, E);
	 *   fsm.addTransition(C, Sleep, A);
	 *
	 *   e.g.
	 *    fsm.setCurrentState(A);
	 *    fsm.trigger(WakeUp);
	 *    At this point, currentState will point to H.
	 *
	 *   e.g.
	 *    fsm.setCurrentState(D);
	 * 	  fsm.trigger(Sleep);
	 *    At this point, currentState will point to C.
	 *
	 *   e.g.
	 *    fsm.setCurrentState(B);
	 * 	  fsm.trigger(Sleep);
	 *    At this point, currentState is still B, because the GO event does not trigger any state.
	 * @param event trigger event name
	 * @return currentState
	 */
	public T trigger(String event) {
		if(currentState == null)
			throw new FiniteStateMachineException("Current State Not Exists");
		if(isEventExists(event))
		{
			int eventIndex = getEventIndexFromEventMap(event);
			StateNode currentNode = getStateNodeByState(currentState);
			currentState = getStateFromMatrix(currentNode.getStateID(), eventIndex);
		}
		return currentState;
	}

	/**
	 * Obtain a state from matrix.
	 * @param row in the matrix.
	 * @param col in the matrix.
	 * @return a state from row and col in matrix.
	 */
	private T getStateFromMatrix(int row, int col) {
		return stateList.get(matrix.get(row).get(col));
	}

	/**
	 * Replaces currentState
	 * @param currentState to be replaced
	 */
	public void setCurrentState(T currentState){
		if(!isStateExists(currentState))
            throw new FiniteStateMachineException("State Not Exist");
		else
			this.currentState = getStateNodeByState(currentState).getState();
	}

	/**
	 * Obtain the stateNode by @param state
	 * @param t the state
	 * @return mapped Statenode
	 */
	private StateNode getStateNodeByState(T t) {
		return stateNodesMap.get(t.hashCode());
	}

	/**
	 * @return <tt>true</tt> if this state exists.
	 */
	private boolean isStateExists(T t){
		return stateNodesMap.containsKey(t.hashCode());
	}

	/**
	 * @return <tt>true</tt> if this event exists.
	 */
	private boolean isEventExists(String event) {
		return eventMap.containsKey(event);
	}

	/**
	 * @param event event
	 * @return event index if this event exists.
	 */
	private int getEventIndexFromEventMap(String event) {
		if(!isEventExists(event))
			throw new FiniteStateMachineException("Event Not Exist");
		return eventMap.get(event);
	}

	/**
	 * Associates @param event with the event index in eventToIndexMap.
	 * Add an event if the event not exists.
	 */
	private void addEvent(String event) {
		if(!isEventExists(event)) {
			eventMap.put(event, eventIndex++);
		}
	}

	/**
	 * @return current state
	 */
	public T getCurrentState() {
		return currentState;
	}


	/**
	 * Add a new state to the stateNodesMap using hashcode as the key ,
	 * and automatically align table lengths.
	 *
	 * The initial value of each column is @param state
	 *
	 * e.g.if the matrix look like:
	 *  |1,2,3
	 * =======
	 * 1|1,1,1
	 * 2|2,2,2
	 * 3|3,3,3
	 *
	 * When a new node added, it will look like this:
	 *  |1,2,3,4
	 * =========
	 * 1|1,1,1,1
	 * 2|2,2,2,2
	 * 3|3,3,3,3
	 * 4|4,4,4,4
	 *
	 * @param t new state
	 */
	public void addState(T t) {
		stateList.add(t);
		int stateNumber = stateList.size()-1;
		StateNode newNode = new StateNode(t,stateNumber);
		stateNodesMap.put(t.hashCode(), newNode);
		ArrayList<Integer> initEventList = new ArrayList<>();
		initEventList.add(stateNumber);
		matrix.add(initEventList);
		for(int i = 0; i < matrix.size(); i++)
		{
			while(matrix.get(i).size() != matrix.size()){
				addNewState(i);
			}
		}
	}

	/**
	 * Add a index of new state in the List.
	 * @param index index of new state.
	 */
	private void addNewState(int index){
		List<Integer> row = matrix.get(index);
		int initStateIndex = matrix.get(index).get(0);
		row.add(initStateIndex);
	}

	public List<T> getStates() {
		return stateList;
	}

	/**
	 * Create a trigger transition with two different states
	 * @param from T that will be changed
	 * @param event the triggering event's name
	 * @param to is triggered state
	 */
	public void addTransition(T from, String event, T to) {
		if(!validateFromStateAndToStateExist(from, to))
			throw new FiniteStateMachineException("Not exists");
		else
		{
			addEvent(event);
			StateNode fromState = getStateNodeByState(from);
			StateNode toState = getStateNodeByState(to);
			setDataInMatrix(fromState.getStateID(), getEventIndexFromEventMap(event), toState.getStateID());
		}
	}

	/**
	 * validate that FromState and ToState exist
	 * @param states State array
	 * @return <tt>true</tt> if all states exists.
	 */
	private boolean validateFromStateAndToStateExist(T ... states) {
		for (T state : states)
			if (!isStateExists(state))
				return false;
		return true;
	}

	/**
	 * Point all states to a specific state(@param targetState), and call this event @param event.
	 * If there are state that does not want to point to a specific state, use @param excepts to filter out these states.
	 * If @param excepts is empty, it will not filter any state.
	 *
	 * e.g. the matrix look like:
	 *   |1,2,3,4,5
	 * 	===========
	 *  1|1,1,1,1,5
	 * 	2|2,2,2,2,5
	 * 	3|3,3,3,3,5
	 * 	4|4,4,4,4,5
	 * 	5|5,5,5,5,5
	 *
	 * 	and call addTransitionFromAllStates("ALL", 3, 5), and matrix look like:
	 *   |1,2,3,4,5
	 * 	===========
	 *  1|1,1,3,1,5
	 * 	2|2,2,3,2,5
	 * 	3|3,3,3,3,5
	 * 	4|4,4,3,4,5
	 * 	5|5,5,5,5,5
	 * @param event event name
	 * @param targetState target state
	 * @param excepts those that want to be filtered out.
	 */
	public void addTransitionFromAllStates(String event, T targetState, T ...excepts) {
		if(!isStateExists(targetState))
            throw new FiniteStateMachineException("Target State Not Exists");
		else
		{
			addEvent(event);
			StateNode targetStateNode = getStateNodeByState(targetState);
			if(excepts.length > 0)
			{
				List<Integer> exceptsNodeList = new ArrayList<>();
				for (T exceptState : excepts)
				{
					StateNode exceptsNode = getStateNodeByState(exceptState);
					exceptsNodeList.add(exceptsNode.getStateID());
				}
				for (int row = 0; row < matrix.size(); row++)
				{
					if(!exceptsNodeList.contains(row))
						setDataInMatrix(row, getEventIndexFromEventMap(event), targetStateNode.getStateID());
				}
			}
			else
				for (ArrayList<Integer> state : matrix)
					state.set(getEventIndexFromEventMap(event), targetStateNode.getStateID());
		}
	}

	/**
	 * Update the data in a matrix.
	 * @param row Matrix row.
	 * @param col Matrix column.
	 * @param newData New data to set.
	 */
	private void setDataInMatrix(int row, int col, int newData){
		matrix.get(row).set(col, newData);
	}

	/**
	 * @return the shallow copied FSM
	 */
	@SuppressWarnings("unchecked")
    public FiniteStateMachine<T> clone(){
		try {
			return (FiniteStateMachine<T>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	private class StateNode {
		private int stateID;
		private T state;

		private StateNode(T state, int stateID){
			this.state = state;
			this.stateID = stateID;
		}

		private int getStateID() {
			return stateID;
		}

		private T getState() {
			return state;
		}

		@Override
		@SuppressWarnings("unchecked")
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			StateNode stateNode = (StateNode) o;
			return stateID == stateNode.stateID &&
					state.equals(stateNode.state);
		}

		@Override
		public int hashCode() {
			return Objects.hash(stateID, state);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FiniteStateMachine<?> that = (FiniteStateMachine<?>) o;
		return eventIndex == that.eventIndex &&
				Objects.equals(currentState, that.currentState) &&
				eventMap.equals(that.eventMap) &&
				stateList.equals(that.stateList) &&
				matrix.equals(that.matrix) &&
				stateNodesMap.equals(that.stateNodesMap);
	}

	@Override
	public int hashCode() {
		return Objects.hash(currentState, eventIndex, eventMap, stateList, matrix, stateNodesMap);
	}
}