package com.pokewords.framework.sprites.components;

import com.pokewords.framework.engine.Events;
import com.pokewords.framework.engine.FiniteStateMachine;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author johnny850807
 */
public class FrameStateMachineComponent extends CloneableComponent implements Shareable, Renderable{
    protected FiniteStateMachine<Frame> fsm = new FiniteStateMachine<>();
    protected Sprite sprite;
    protected AppStateWorld world;


    public FrameStateMachineComponent() {
    }

    @Override
    public void onAppStateStart(AppStateWorld world) {
        this.world = world;
    }

    @Override
    public void onAppStateEnter() {

    }

    @Override
    public void onUpdate(double tpf) {
        trigger(Events.UPDATE);
        Frame frame = getCurrentFrame();
        frame.apply(world, sprite);
    }


    public void addFrame(Frame frame){
        fsm.addState(frame);
    }

    public void addTransition(Frame from, String event, Frame to){
        fsm.addTransition(from, event, to);
    }

    public void addTransitionFromAllFrames(String event, Frame targetFrame){
        fsm.addTransitionFromAllStates(event, targetFrame);
    }

    public Frame trigger(String event){
        return fsm.trigger(event);
    }

    public Frame getCurrentFrame(){
        return fsm.getCurrentState();
    }

    public void setCurrentFrame(Frame frame){
        this.fsm.setCurrentState(frame);
    }

    @Override
    public void onAppStateExit() {

    }

    @Override
    public void onAppStateDestroy() {

    }


    public FiniteStateMachine<Frame> getFsm() {
        return fsm;
    }

    public void setFsm(FiniteStateMachine<Frame> fsm) {
        this.fsm = fsm;
    }

    /**
     * @return the actual inner finite state machine which contains the frames and transitions
     */
    public FiniteStateMachine<Frame> getFiniteStateMachine(){
        return fsm;
    }

    @Override
    public FrameStateMachineComponent clone() {
        return (FrameStateMachineComponent) super.clone();
    }

    @Override
    public String toString() {
        return String.format("\nFSM: \n%s\n", fsm);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrameStateMachineComponent that = (FrameStateMachineComponent) o;
        return fsm.equals(that.fsm) &&
                Objects.equals(sprite, that.sprite) &&
                Objects.equals(world, that.world);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fsm, sprite, world);
    }

    /**
     * @return all the frames (states) that this finite state machine owns
     */
    public List<Frame> getFrames() {
        return fsm.getStates();
    }

    /**
     * @return get all the frames that rendered in the present loop, expected to be only one frame,
     * which is the current frame (state).
     */
    @Override
    public List<Frame> getCurrentFrames() {
        LinkedList<Frame> frames = new LinkedList<>();
        frames.add(getCurrentFrame());
        return frames;
    }
}

