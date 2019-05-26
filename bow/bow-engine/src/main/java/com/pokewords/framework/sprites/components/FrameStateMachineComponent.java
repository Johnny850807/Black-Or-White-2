package com.pokewords.framework.sprites.components;

import com.pokewords.framework.commons.FiniteStateMachine;
import com.pokewords.framework.engine.Events;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.frames.EffectFrame;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.components.marks.Renderable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author johnny850807
 */
public class FrameStateMachineComponent extends CloneableComponent implements Renderable {
    protected Map<Integer, EffectFrame> effectFrameMap = new HashMap<>(); // <Frame's event, Frame>
    protected long latestUpdateTimestamp = System.currentTimeMillis();
    protected int frameDurationCountdown = 0;

    protected FiniteStateMachine<EffectFrame> fsm = new FiniteStateMachine<>();
    protected LinkedList<EffectFrame> renderedFrameCollection = new LinkedList<>();



    public EffectFrame getFrame(int id) {
        return effectFrameMap.get(id);
    }


    @Override
    public void onComponentAttachedSprite(Sprite sprite) {
        super.onComponentAttachedSprite(sprite);
        fsm.getStates().forEach(frame -> frame.boundToSprite(sprite));
    }

    public void addFrame(EffectFrame frame){
        fsm.addState(frame);
        if (hasOwnerSprite())
            frame.boundToSprite(getOwnerSprite());
        this.effectFrameMap.put(frame.getId(), frame);
    }

    @Override
    public void onComponentDetachedSprite(Sprite sprite) {
        fsm.getStates().forEach(frame -> frame.boundToSprite(null));
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
        frame.apply(getAttachedWorld(), getOwnerSprite());
        renderedFrameCollection.clear();
        renderedFrameCollection.add(frame);

        latestUpdateTimestamp = System.currentTimeMillis();
    }


    public void addTransition(EffectFrame from, Object event, EffectFrame to){
        fsm.addTransition(from, event, to);
    }

    public void addTransitionFromAllFrames(Object event, EffectFrame targetFrame){
        fsm.addTransitionFromAllStates(event, targetFrame);
    }

    public EffectFrame trigger(Object event){
        return fsm.trigger(event.toString());
    }

    public EffectFrame getCurrentFrame(){
        return fsm.getCurrentState();
    }

    public void setCurrentFrame(EffectFrame frame){
        this.fsm.setCurrentState(frame);
        renderedFrameCollection.clear();
        renderedFrameCollection.add(frame);
    }


    /**
     * @return the actual inner finite state machine which containsSprite the frames and transitions
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
        clone.fsm = this.fsm.clone();
        cloneRenderedFrameCollectionWithEffectFrameMap(clone);
        return clone;
    }

    private void cloneRenderedFrameCollectionWithEffectFrameMap(FrameStateMachineComponent clone) {
        clone.effectFrameMap = new HashMap<>();

        clone.renderedFrameCollection = renderedFrameCollection.stream()
                                    .map(EffectFrame::clone)
                                    .collect(Collectors.toCollection(LinkedList::new));

        clone.getAllFrames().forEach(f -> clone.effectFrameMap.put(f.getId(), f.clone()));
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
                renderedFrameCollection.equals(that.renderedFrameCollection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(effectFrameMap, fsm, renderedFrameCollection);
    }

    @Override
    public Collection<EffectFrame> getAllFrames() {
        return fsm.getStates();
    }

    /**
     * @return get all the frames that rendered in the present loop, expected to be only one frame,
     * which is the current frame (state).
     */
    @Override
    public Collection<? extends Frame> getRenderedFrames() {
        return renderedFrameCollection;
    }


}

