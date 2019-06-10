package com.pokewords.framework.commons;

import com.pokewords.framework.engine.exceptions.FiniteStateMachineException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * The Shawn's implementation of FiniteStateMachine.
 *
 * @author Shawn
 * @deprecated
 */
public class ShawnFiniteStateMachine<T> implements Cloneable {
    private T currentState;

    /**
     * Use index to save event.
     */
    private ArrayList<T> stateList = new ArrayList<>();
    private HashMap<String, Integer> eventMap = new HashMap<>();  // <event's name, event's id>
    private ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
    private HashMap<Integer, StateNode> stateNodesMap = new HashMap<>();  // <state's hashcode, state's node>


    /**
     * Show the overall status and currentState of the current matrix.
     * The first number of each line is the index of the first state of each line,
     * e.g. 3|(0)122,(1)target,(2)z --> first state is '122' and it's index is 3.
     * <p>
     * If the other states are the same (pointing to itself), use "-" instead
     * <p>
     * e.g. originally might be like this:
     * 2|(0)2,(1)2,(2)2,(3)1,(4)2,(5)2
     * become to ->
     * 2|(0)2,-,-,(3)1,-,-
     *
     * @return Complete matrix string
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Current State：").append(currentState);
        result.append("\n\n");
        for (int i = 0; i < matrix.size(); i++) {
            result.append(i).append("|");
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j).equals(matrix.get(i).get(0)) &&
                        j != 0) {
                    result.append("-");
                } else {
                    result.append("(").append(j).append(")");
                    result.append(matrix.get(i).get(j));
                }
                result.append(",");
            }
            result.deleteCharAt(result.length() - 1);
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * through the event to trigger the corresponding state, so that currentState points to the triggered state.
     * If the event does not exist, do nothing and currentState is returned.
     * e.g.
     * fsm.addState(A);
     * fsm.addState(B);
     * fsm.addState(C);
     * fsm.addState(D);
     * fsm.addState(E);
     * fsm.addState(F);
     * fsm.addState(G);
     * fsm.addState(H);
     * <p>
     * fsm.addTransition(A,WakeUp,H);
     * fsm.addTransition(B,GO,H);
     * fsm.addTransition(D,Sleep,C);
     * fsm.addTransition(B,WakeUp,E);
     * fsm.addTransition(C,Sleep,A);
     * <p>
     * e.g.
     * fsm.setCurrentState(A);
     * fsm.trigger(WakeUp);
     * At this point, currentState will point to H.
     * <p>
     * e.g.
     * fsm.setCurrentState(D);
     * fsm.trigger(Sleep);
     * At this point, currentState will point to C.
     * <p>
     * e.g.
     * fsm.setCurrentState(B);
     * fsm.trigger(Sleep);
     * At this point, currentState is still B, because the GO event does not trigger any state.
     *
     * @param event trigger event name
     * @return currentState
     */
    public T trigger(String event) {
        if (currentState == null) {
            throw new FiniteStateMachineException("currentState not exists");
        }
        if (eventExists(event)) {
            int eventIndex = getEventIndexFromEventMap(event);
            StateNode currentNode = getStateNodeByState(currentState);
            int currentNumber = matrix.get(currentNode.getStateNumber()).get(eventIndex);
            currentState = stateList.get(currentNumber);
        }
        return currentState;
    }

    /**
     * Replaces currentState
     *
     * @param currentState to be replaced
     */
    public void setCurrentState(T currentState) {
        if (!stateExists(currentState)) {
            throw new FiniteStateMachineException("State not exist");
        } else {
            this.currentState = getStateNodeByState(currentState).getState();
        }
    }

    /**
     * Obtain the stateNode by @param state
     *
     * @param t the state
     * @return mapped Statenode
     */
    private StateNode getStateNodeByState(T t) {
        return stateNodesMap.get(t.hashCode());
    }

    /**
     * @return <tt>true</tt> if this state exists.
     */
    private boolean stateExists(T t) {
        return t != null && stateNodesMap.containsKey(t.hashCode());
    }

    /**
     * @return <tt>true</tt> if this event exists.
     */
    private boolean eventExists(String event) {
        return event != null && eventMap.containsKey(event);
    }

    /**
     * @param event event
     * @return event index if this event exists.
     */
    private int getEventIndexFromEventMap(String event) {
        return eventMap.get(event);
    }

    /**
     * Associates @param event with the event index in eventToIndexMap.
     * Add an event if the event not exists.
     */
    private void addEvent(String event) {
        if (!eventExists(event)) {
            eventMap.put(event, eventMap.size());
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
     * <p>
     * The initial value of each column is @param state
     * <p>
     * e.g.if the matrix look like:
     * |1,2,3
     * =======
     * 1|1,1,1
     * 2|2,2,2
     * 3|3,3,3
     * <p>
     * When a new node added, it will look like this:
     * |1,2,3,4
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
        int stateNumber = stateList.size() - 1;
        StateNode newNode = new StateNode(t, stateNumber);
        stateNodesMap.put(t.hashCode(), newNode);
        ArrayList<Integer> initEventList = new ArrayList<>();
        initEventList.add(stateNumber);
        matrix.add(initEventList);
        for (int i = 0; i < matrix.size(); i++) {
            while (matrix.get(i).size() != matrix.size()) {
                matrix.get(i).add(matrix.get(i).get(0));
            }
        }
    }

    public ArrayList<T> getStates() {
        return stateList;
    }


    public void addTransition(T from, Object event, T to) {
        addTransition(from, event.toString(), to);
    }

    /**
     * Create a trigger transition with two different states
     *
     * @param from  T that will be changed
     * @param event the triggering event's name
     * @param to    is triggered state
     */
    public void addTransition(T from, String event, T to) {
        if (!stateExists(from) || !stateExists(to)) {
            throw new FiniteStateMachineException("Not exists");
        } else {
            addEvent(event);
            StateNode fromState = getStateNodeByState(from);
            StateNode toState = getStateNodeByState(to);
            matrix.get(fromState.getStateNumber()).set(getEventIndexFromEventMap(event), toState.getStateNumber());
        }
    }

    public void addTransitionFromAllStates(Object event, T targetState, T... excepts) {
        addTransitionFromAllStates(event.toString(), targetState, excepts);
    }

    /**
     * Point all states to a specific state(@param targetState), and call this event @param event.
     * If there are state that does not want to point to a specific state, use @param excepts to filter out these states.
     * If @param excepts is empty, it will not filter any state.
     * <p>
     * e.g. the matrix look like:
     * |1,2,3,4,5
     * ===========
     * 1|1,1,1,1,5
     * 2|2,2,2,2,5
     * 3|3,3,3,3,5
     * 4|4,4,4,4,5
     * 5|5,5,5,5,5
     * <p>
     * and call addTransitionFromAllStates("ALL", 3, 5), and matrix look like:
     * |1,2,3,4,5
     * ===========
     * 1|1,1,3,1,5
     * 2|2,2,3,2,5
     * 3|3,3,3,3,5
     * 4|4,4,3,4,5
     * 5|5,5,5,5,5
     *
     * @param event       event name
     * @param targetState target state
     * @param excepts     those that want to be filtered out.
     */
    public void addTransitionFromAllStates(String event, T targetState, T... excepts) {
        if (!stateExists(targetState)) {
            throw new FiniteStateMachineException("targetState not exists");
        } else {
            addEvent(event);
            StateNode targetStateNode = getStateNodeByState(targetState);
            if (excepts.length > 0) {
                List<Integer> exceptsNodeList = new ArrayList<>();
                for (T exceptState : excepts) {
                    StateNode exceptsNode = getStateNodeByState(exceptState);
                    exceptsNodeList.add(exceptsNode.getStateNumber());
                }
                for (int i = 0; i < matrix.size(); i++) {
                    if (!exceptsNodeList.contains(i)) {
                        matrix.get(i).set(getEventIndexFromEventMap(event), targetStateNode.getStateNumber());
                    }
                }
            } else {
                for (ArrayList<Integer> state : matrix) {
                    state.set(getEventIndexFromEventMap(event), targetStateNode.getStateNumber());
                }
            }
        }
    }

    /**
     * @return the deep clone of FSM
     */
    @SuppressWarnings("unchecked")
    public ShawnFiniteStateMachine<T> clone() {
        try {
            ShawnFiniteStateMachine<T> clone = (ShawnFiniteStateMachine<T>) super.clone();
            clone.matrix = (ArrayList<ArrayList<Integer>>) this.matrix.clone();
            clone.eventMap = (HashMap<String, Integer>) this.eventMap.clone();
            clone.stateList = (ArrayList<T>) this.stateList.clone();
            clone.stateNodesMap = (HashMap<Integer, StateNode>) this.stateNodesMap.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private class StateNode implements Cloneable {
        private int stateNumber;
        private T state;

        private StateNode(T state, int stateNumber) {
            this.state = state;
            this.stateNumber = stateNumber;
        }

        private int getStateNumber() {
            return stateNumber;
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
            return stateNumber == stateNode.stateNumber &&
                    state.equals(stateNode.state);
        }

        @Override
        public int hashCode() {
            return Objects.hash(stateNumber, state);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShawnFiniteStateMachine<?> that = (ShawnFiniteStateMachine<?>) o;
        return Objects.equals(currentState, that.currentState) &&
                eventMap.equals(that.eventMap) &&
                stateList.equals(that.stateList) &&
                matrix.equals(that.matrix) &&
                stateNodesMap.equals(that.stateNodesMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentState, eventMap, stateList, matrix, stateNodesMap);
    }
}