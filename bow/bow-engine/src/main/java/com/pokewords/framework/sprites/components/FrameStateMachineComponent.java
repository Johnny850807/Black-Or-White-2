package com.pokewords.framework.sprites.components;

import com.pokewords.framework.engine.Events;
import com.pokewords.framework.engine.FiniteStateMachine;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

import java.util.Collection;

/**
 * @author johnny850807
 */
public class FrameStateMachineComponent extends FiniteStateMachine<Frame>
        implements Component {
    private AppStateWorld appStateWorld;
    private PropertiesComponent propertiesComponent;


    public FrameStateMachineComponent() {
    }

    public FrameStateMachineComponent(PropertiesComponent propertiesComponent) {
        this.propertiesComponent = propertiesComponent;
    }

    public FrameStateMachineComponent(AppStateWorld appStateWorld, PropertiesComponent propertiesComponent) {
        this.appStateWorld = appStateWorld;
        this.propertiesComponent = propertiesComponent;
    }

    /**
     * This boolean indicates if the Sprite's state is triggered after the Sprite's state has been modified,
     * (i.e. if the Sprite's state has been modified, we must trigger it in the State Machine.)
     * If it's not triggered (false), the state's suppose to be triggered at the next onUpdate().
     */
    private boolean stateTriggered = false;

    @Override
    public void onBoundToSprite(Sprite sprite) {
        propertiesComponent.addStateListener(state -> stateTriggered = false);
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onUpdate() {
        triggerTheCurrentState();
        applyTheFrameEffect();
    }

    private void triggerTheCurrentState() {
        if (!stateTriggered) {
            stateTriggered = true;
            trigger(propertiesComponent.getState());
        } else
            trigger(Events.UPDATE);
    }

    private void applyTheFrameEffect() {
        Frame frame = getCurrentState();
        frame.apply(appStateWorld);
    }


    public void setAppStateWorld(AppStateWorld appStateWorld) {
        this.appStateWorld = appStateWorld;
    }

    public void setPropertiesComponent(PropertiesComponent propertiesComponent) {
        this.propertiesComponent = propertiesComponent;
    }
}
