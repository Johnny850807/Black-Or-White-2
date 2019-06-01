package com.pokewords.framework.commons;

import com.pokewords.framework.engine.exceptions.FiniteStateMachineException;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * The table implementation of FiniteStateMachine.
 *
 * @author Shawn
 */
public class FiniteStateMachine<T> implements Cloneable {
    private T currentState;
    private int currentStateId;
    private HashMap<T, Integer> stateIdMap = new HashMap<>();  // <state, state's id>
    private HashMap<Object, Integer> eventIdMap = new HashMap<>();// <event, event's id>
    private HashMap<Point, Integer> transitionTable = new HashMap<>(); // <(fromState's id, event's id), toState's id>


    @Override
    public String toString() {
        return "";
    }

    /**
     * Set the newCurrentState
     * @param newCurrentState the new newCurrentState
     */
    public void setCurrentState(T newCurrentState) {
        validateStateExists(newCurrentState);
        this.currentState = newCurrentState;
        this.currentStateId = stateIdMap.get(currentState);
    }


    /**
     * @return current state
     */
    public T getCurrentState() {
        return currentState;
    }


    public void addState(T state) {
        if (stateIdMap.containsKey(state))
            throw new FiniteStateMachineException("The state " + state + " has already been added.");
        int id = stateIdMap.size();
        stateIdMap.put(state, id);
    }


    public Collection<T> getStates() {
        return stateIdMap.keySet();
    }

    @SafeVarargs
    public final void addTransitionFromAllStates(Object event, T targetState, T... excepts) {
        Collection<T> fromStates = new HashSet<>(getStates());
        fromStates.removeAll(Arrays.asList(excepts));

        for (T fromState : fromStates) {
            addTransition(fromState, event, targetState);
        }
    }

    public void addTransition(T from, Object event, T to) {
        validateStateExists(from);
        validateStateExists(to);

        int fromStateId = stateIdMap.get(from);
        int eventId = getOrCreateEventId(event);
        int toStateId = stateIdMap.get(to);

        transitionTable.put(new Point(fromStateId, eventId), toStateId);
    }

    private void validateStateExists(T state) {
        if (!stateIdMap.containsKey(state))
            throw new FiniteStateMachineException("The state " + state + " doesn't exist.");
    }

    private int getOrCreateEventId(Object event) {
        if (eventIdMap.containsKey(event))
            return eventIdMap.get(event);

        int eventId = eventIdMap.size();
        eventIdMap.put(event, eventId);
        return eventId;
    }


    public T trigger(Object event) {
        validateCurrentStateHasBeenSet();

        int currentStateId = stateIdMap.get(currentState);
        int eventId = eventIdMap.getOrDefault(event, -1);
        int toStateId = transitionTable.getOrDefault(new Point(currentStateId, eventId), currentStateId);

        this.currentState = getStateById(toStateId);
        this.currentStateId = stateIdMap.get(currentState);
        return currentState;
    }

    private void validateCurrentStateHasBeenSet() {
        if (currentState == null)
            throw new FiniteStateMachineException("The currentState has not been set");
    }

    private T getStateById(int stateId) {
        return stateIdMap.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(stateId))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);
    }

    /**
     * @return the deep clone of FSM
     */
    @SuppressWarnings("unchecked")
    public FiniteStateMachine<T> clone() {
        try {
            FiniteStateMachine<T> clone = (FiniteStateMachine<T>) super.clone();
            clone.stateIdMap = cloneStateIdMap();
            clone.eventIdMap = (HashMap<Object, Integer>) this.eventIdMap.clone();
            clone.transitionTable = (HashMap<Point, Integer>) this.transitionTable.clone();
            clone.currentState = clone.getStateById(clone.currentStateId);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private HashMap<T, Integer> cloneStateIdMap() {
        HashMap<T, Integer> cloneMap = new HashMap<>();

        for (T state : stateIdMap.keySet()) {
            if (state instanceof Cloneable)
                cloneMap.put(cloneStateByReflection(state), stateIdMap.get(state));
            else
                cloneMap.put(state, stateIdMap.get(state));

        }

        return cloneMap;
    }

    @SuppressWarnings("unchecked")
    private T cloneStateByReflection(T state) {
        try {
            return (T) state.getClass().getMethod("clone").invoke(state);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new InternalError(e);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FiniteStateMachine<?> that = (FiniteStateMachine<?>) o;
        boolean a = Objects.equals(currentState, that.currentState) ;
        boolean b =        stateIdMap.equals(that.stateIdMap) ;
        boolean c =        eventIdMap.equals(that.eventIdMap);
        boolean d =       transitionTable.equals(that.transitionTable);
        return a && b && c && d;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentState, stateIdMap, eventIdMap, transitionTable);
    }
}