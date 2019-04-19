package com.pokewords.framework.sprites.components;

import com.pokewords.framework.engine.Events;
import com.pokewords.framework.engine.FiniteStateMachine;
import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

import java.lang.reflect.Field;
import java.text.Format;
import java.util.Objects;

/**
 * @author johnny850807
 */
public class FrameStateMachineComponent extends Component implements Shareable{
    private FiniteStateMachine<Frame> fsm = new FiniteStateMachine<>();
    private Sprite sprite;
    private AppStateWorld world;
    private PropertiesComponent propertiesComponent;


    public FrameStateMachineComponent() {
    }

    /**
     * This boolean indicates if the Sprite's state is triggered after the Sprite's state has been modified,
     * (i.e. if the Sprite's state has been modified, we must trigger it in the State Machine.)
     * If it's not triggered (false), the state's suppose to be triggered at the next onUpdate().
     */
    private boolean stateTriggered = false;

    @Override
    public void onAppStateStart(AppStateWorld world) {
        this.world = world;
        propertiesComponent.addStateListener(state -> stateTriggered = false);
    }

    @Override
    public void onAppStateEnter() {

    }

    @Override
    public void onUpdate(double tpf) {
        triggerTheCurrentState();
        applyTheFrameEffect();
    }



    private void triggerTheCurrentState() {
        if (!stateTriggered) {
            trigger(propertiesComponent.getState());
            stateTriggered = true;
        } else
            trigger(Events.UPDATE);
    }

    private void applyTheFrameEffect() {
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

    @Override
    public void onAppStateExit() {

    }

    @Override
    public void onAppStateDestroy() {

    }

    public AppStateWorld getAppStateWorld() {
        return world;
    }

    public PropertiesComponent getPropertiesComponent() {
        return propertiesComponent;
    }

    public boolean isStateTriggered() {
        return stateTriggered;
    }

    public void setAppStateWorld(AppStateWorld appStateWorld) {
        this.world = appStateWorld;
    }

    public void setPropertiesComponent(PropertiesComponent propertiesComponent) {
        this.propertiesComponent = propertiesComponent;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrameStateMachineComponent that = (FrameStateMachineComponent) o;
        return stateTriggered == that.stateTriggered &&
                fsm.equals(that.fsm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fsm, stateTriggered);
    }
}

