package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.components.marks.Shareable;

/**
 * A component mark a Sprite as physical.
 * Two physical sprites will be blocked by each other when they are collided together.
 * @author johnny850807 (waterball)
 */
public class PhysicsComponent extends Component implements Shareable {
    private static final PhysicsComponent instance = new PhysicsComponent();

    public static PhysicsComponent getInstance() {
        return instance;
    }
}
