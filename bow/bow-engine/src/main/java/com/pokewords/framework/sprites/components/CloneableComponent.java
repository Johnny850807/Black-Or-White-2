package com.pokewords.framework.sprites.components;

/**
 * Component implements a clone() method and marked with the Cloneable interface.
 * @author johnny850807 (waterball)
 */
public abstract class CloneableComponent extends Component implements Cloneable {

    public CloneableComponent clone(){
        try {
            return (CloneableComponent) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
