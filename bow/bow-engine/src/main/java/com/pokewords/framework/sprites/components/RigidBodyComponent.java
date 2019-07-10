package com.pokewords.framework.sprites.components;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * A component mark a Sprite as rigid-body.
 * Two rigid-body sprites will be blocked by each other when they are collided together.
 * @author johnny850807 (waterball)
 */
public final class RigidBodyComponent extends CloneableComponent {
    private Collection<Object> ignoredTypes;

    public RigidBodyComponent(Collection<Object> ignoredTypes) {
        this.ignoredTypes = ignoredTypes;
    }

    public static RigidBodyComponent getInstance() {
        return new RigidBodyComponent(Collections.emptySet());
    }

    /**
     * @param ignoredTypes the ignored sprite types, that is, the owner of this component
     *                     won't have rigid-body collision with those sprite whose type is one of the ignoredTypes.
     */
    public static RigidBodyComponent withIgnoredTypes(Object ...ignoredTypes) {
        return new RigidBodyComponent(new HashSet<>(Arrays.asList(ignoredTypes)));
    }

    public Collection<Object> getIgnoredTypes() {
        return ignoredTypes;
    }

    @Override
    public CloneableComponent clone() {
        RigidBodyComponent clone = (RigidBodyComponent) super.clone();
        if (!ignoredTypes.isEmpty())
            clone.ignoredTypes = new HashSet<>(ignoredTypes);
        return clone;
    }
}
