package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.components.marks.Shareable;

/**
 * A component mark a Sprite as rigid-body.
 * Two rigid-body sprites will be blocked by each other when they are collided together.
 * @author johnny850807 (waterball)
 */
public class RigidBodyComponent extends Component implements Shareable {
    private static final RigidBodyComponent instance = new RigidBodyComponent();

    public static RigidBodyComponent getInstance() {
        return instance;
    }

    private RigidBodyComponent() {}
}
