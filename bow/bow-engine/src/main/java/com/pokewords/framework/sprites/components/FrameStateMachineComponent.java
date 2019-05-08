package com.pokewords.framework.sprites.components;

import com.pokewords.framework.engine.Events;
import com.pokewords.framework.commons.FiniteStateMachine;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.frames.EffectFrame;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.components.marks.Renderable;
import com.pokewords.framework.sprites.components.marks.Shareable;

import java.util.*;

/**
 * @author johnny850807
 */
public class FrameStateMachineComponent extends CloneableComponent implements Shareable, Renderable {
    protected FiniteStateMachine<EffectFrame> fsm = new FiniteStateMachine<>();
    protected Sprite sprite;
    protected AppStateWorld world;
    protected final LinkedList<EffectFrame> renderedFrame = new LinkedList<>();

    @Override
    public void onAppStateCreate(AppStateWorld world) {
        this.world = world;
    }

    @Override
    public void onAppStateEnter() { }

    @Override
    public void onUpdate(int timePerFrame) {
        trigger(Events.UPDATE);
        EffectFrame frame = getCurrentFrame();
        frame.apply(world, sprite);
        renderedFrame.clear();
        renderedFrame.add(frame);
    }


    public void addFrame(EffectFrame frame){
        fsm.addState(frame);
    }

    public void addTransition(EffectFrame from, String event, EffectFrame to){
        fsm.addTransition(from, event, to);
    }

    public void addTransitionFromAllFrames(String event, EffectFrame targetFrame){
        fsm.addTransitionFromAllStates(event, targetFrame);
    }

    public EffectFrame trigger(String event){
        return fsm.trigger(event);
    }

    public EffectFrame getCurrentFrame(){
        return fsm.getCurrentState();
    }

    public void setCurrentFrame(EffectFrame frame){
        this.fsm.setCurrentState(frame);
    }

    @Override
    public void onAppStateExit() { }

    @Override
    public void onAppStateDestroy() { }


    public FiniteStateMachine<EffectFrame> getFsm() {
        return fsm;
    }

    public void setFsm(FiniteStateMachine<EffectFrame> fsm) {
        this.fsm = fsm;
    }

    /**
     * @return the actual inner finite state machine which contains the frames and transitions
     */
    public FiniteStateMachine<EffectFrame> getFiniteStateMachine(){
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


    @Override
    public Collection<? extends Frame> getAllFrames() {
        return fsm.getStates();
    }

    /**
     * @return get all the frames that rendered in the present loop, expected to be only one frame,
     * which is the current frame (state).
     */
    @Override
    public Collection<? extends Frame> getRenderedFrames() {
        return renderedFrame;
    }
}

