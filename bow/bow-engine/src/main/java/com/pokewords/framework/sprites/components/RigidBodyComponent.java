package com.pokewords.framework.sprites.components;

/**
 * A component mark a Sprite as rigid-body.
 * Two rigid-body sprites will be blocked by each other when they are collided together.
 * @author johnny850807 (waterball)
 */
public final class RigidBodyComponent extends Component {
    private static final RigidBodyComponent instance = new RigidBodyComponent();

    public static RigidBodyComponent getInstance() {
        return instance;
    }

    private RigidBodyComponent() {}
}
