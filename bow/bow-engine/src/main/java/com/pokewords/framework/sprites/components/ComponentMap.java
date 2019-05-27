package com.pokewords.framework.sprites.components;

import com.pokewords.framework.engine.exceptions.MandatoryComponentRequiredException;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.components.marks.Renderable;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ComponentMap extends HashMap<Class<? extends Component>, Component> implements Renderable {
    private Set<Renderable> renderableComponents = new HashSet<>();

    public void foreachCloneableComponent(Consumer<? super CloneableComponent> consumer) {
        values().stream()
                .filter(c -> c instanceof CloneableComponent)
                .map(c -> (CloneableComponent)c).forEach(consumer);
    }

    public void foreachComponent(Consumer<? super Component> consumer) {
        values().forEach(consumer);
    }

    @Override
    public boolean remove(Object type, Object component) {
        if (!containsKey(type))
            throw new IllegalArgumentException("The component " + type + " does not exist.");
        if (component instanceof Renderable)
            this.renderableComponents.remove(component);
        if (component instanceof PropertiesComponent)
            throw new MandatoryComponentRequiredException("PropertiesComponent cannot be removed.");
        return super.remove(type, component);
    }

    @Override
    public Component put(Class<? extends Component> type, Component component) {
        if (component == null)
            throw new IllegalArgumentException(String.format("The put component (%s) is given a null reference.",type.getSimpleName()));
        if (component instanceof Renderable)
            this.renderableComponents.add((Renderable) component);

        return super.put(type, component);
    }


    public Collection<Renderable> getRenderableComponents() {
        return renderableComponents;
    }

    @Override
    public Collection<? extends Frame> getAllFrames() {
        return renderableComponents.stream()
                        .flatMap(r -> r.getAllFrames().stream())
                        .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends Frame> getRenderedFrames() {
        return renderableComponents.stream()
                .flatMap(r -> r.getRenderedFrames().stream())
                .collect(Collectors.toSet());
    }


    public Set<CloneableComponent> getCloneableComponents(){
        return values().stream()
                .filter(c ->c instanceof CloneableComponent)
                .map(c -> (CloneableComponent) c)
                .collect(Collectors.toSet());
    }


    @Override
    public ComponentMap clone() {
        ComponentMap clone = new ComponentMap();
        copyComponents(clone);
        recacheRenderableComponents(clone);
        return clone;
    }

    private void copyComponents(ComponentMap componentMap) {
        for (Class<? extends Component> type : this.keySet())
        {
            Component component = this.get(type);
            if (isComponentSharedOnly(component))
                componentMap.put(type, component);
            else
                componentMap.put(type, ((CloneableComponent)component).clone());
        }
    }


    private static boolean isComponentSharedOnly(Component component) {
        return !(component instanceof CloneableComponent);
    }

    private static void recacheRenderableComponents(ComponentMap componentMap) {
        componentMap.renderableComponents = componentMap.values().stream()
                                                        .filter(c -> c instanceof Renderable)
                                                        .map(c -> (Renderable) c)
                                                        .collect(Collectors.toSet());

    }
}
