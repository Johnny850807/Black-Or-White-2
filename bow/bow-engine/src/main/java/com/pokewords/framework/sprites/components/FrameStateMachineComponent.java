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

    /**
     * This boolean indicates if the state is triggered after the state is modified,
     * if it's not triggered, we expect it will be triggered at the next onUpdate().
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
        trigger();
        applyTheFrameEffect();
    }

    private void trigger() {
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

}
