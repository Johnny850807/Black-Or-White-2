package com.pokewords.framework.sprites.components;

import com.pokewords.framework.engine.Events;
import com.pokewords.framework.engine.FiniteStateMachine;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

/**
 * @author johnny850807
 */
public class FrameStateMachineComponent extends FiniteStateMachine<Frame> implements Component {
    private AppStateWorld world;
    private PropertiesComponent propertiesComponent;


    public FrameStateMachineComponent() {
    }

    public FrameStateMachineComponent(PropertiesComponent propertiesComponent) {
        this.propertiesComponent = propertiesComponent;
    }

    public FrameStateMachineComponent(AppStateWorld world, PropertiesComponent propertiesComponent) {
        this.world = world;
        this.propertiesComponent = propertiesComponent;
    }

    /**
     * This boolean indicates if the Sprite's state is triggered after the Sprite's state has been modified,
     * (i.e. if the Sprite's state has been modified, we must trigger it in the State Machine.)
     * If it's not triggered (false), the state's suppose to be triggered at the next onUpdate().
     */
    private boolean stateTriggered = false;

    @Override
    public void onAppStateInit(AppStateWorld world) {
        this.world = world;
        propertiesComponent.addStateListener(state -> stateTriggered = false);
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
        Frame frame = getCurrentState();
        frame.apply(world);
    }

    @Override
    public void onAppStateEnter() {

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

    @Override
    public FrameStateMachineComponent clone() {
        try {
            FrameStateMachineComponent clone = (FrameStateMachineComponent) super.clone();
            clone.world = this.world;
            clone.propertiesComponent = this.propertiesComponent.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

