package com.pokewords.framework.sprites.components;

import com.pokewords.framework.engine.Events;
import com.pokewords.framework.commons.FiniteStateMachine;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.frames.EffectFrame;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.components.marks.Renderable;

import java.util.*;

/**
 * @author johnny850807
 */
public class FrameStateMachineComponent extends CloneableComponent implements Renderable {
    protected Sprite sprite;
    protected AppStateWorld world;
    protected Map<Integer, EffectFrame> effectFrameMap = new HashMap<>(); // <Frame's id, Frame>
    protected long latestUpdateTimestamp = System.currentTimeMillis();
    protected int frameDurationCountdown = 0;

    protected FiniteStateMachine<EffectFrame> fsm = new FiniteStateMachine<>();
    protected LinkedList<EffectFrame> renderedFrame = new LinkedList<>();


    public EffectFrame getFrame(int id) {
        return effectFrameMap.get(id);
    }

    @Override
    public void onComponentInjected() {
        fsm.getStates().forEach(frame -> frame.setSprite(sprite));
    }

    @Override
    public void onUpdate(double timePerFrame) {
        frameDurationCountdown -= System.currentTimeMillis() - latestUpdateTimestamp;
        if (frameDurationCountdown <= 0)
        {
            trigger(Events.UPDATE);
            frameDurationCountdown = getCurrentFrame().getDuration();
        }
        EffectFrame frame = getCurrentFrame();
        frame.apply(world, sprite);
        renderedFrame.clear();
        renderedFrame.add(frame);

        latestUpdateTimestamp = System.currentTimeMillis();
    }


    public void addFrame(EffectFrame frame){
        fsm.addState(frame);
        this.effectFrameMap.put(frame.getId(), frame);
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
        renderedFrame.clear();
        renderedFrame.add(frame);
    }


    /**
     * @return the actual inner finite state machine which contains the frames and transitions
     */
    public FiniteStateMachine<EffectFrame> getFiniteStateMachine(){
        return fsm;
    }

    /**
     * @return Shallow clone
     */
    @Override
    public FrameStateMachineComponent clone() {
        FrameStateMachineComponent clone = (FrameStateMachineComponent) super.clone();
        clone.renderedFrame = new LinkedList<>(this.renderedFrame);
        clone.fsm = this.fsm.clone();
        return clone;
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
        return effectFrameMap.equals(that.effectFrameMap) &&
                fsm.equals(that.fsm) &&
                renderedFrame.equals(that.renderedFrame);
    }

    @Override
    public int hashCode() {
        return Objects.hash(effectFrameMap, fsm, renderedFrame);
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

