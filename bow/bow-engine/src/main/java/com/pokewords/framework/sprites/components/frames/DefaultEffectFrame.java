package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.Sprite;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public abstract class DefaultEffectFrame extends SerializableFrame implements EffectFrame {
    private int id;
    private int duration;

    private ArrayList<GameEffect> effects = new ArrayList<>();  // the effects will be shared among effectFrame of the same prototype
    private Set<GameEffect> appliedGameEffects = Collections.newSetFromMap(new IdentityHashMap<>());

    public DefaultEffectFrame(int id, int layerIndex, int duration) {
        super(layerIndex);
        this.id = id;
        this.duration = duration;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public void apply(AppStateWorld gameWorld, Sprite sprite) {
        for (GameEffect effect : effects) {
            if (!appliedGameEffects.contains(effect))
            {
                effect.onFirstApply(gameWorld, sprite);
                appliedGameEffects.add(effect);
            }
            effect.apply(gameWorld, sprite);
        }
    }

    @Override
    public void addEffect(GameEffect effect) {
        effects.add(effect);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DefaultEffectFrame that = (DefaultEffectFrame) o;
        return id == that.id &&
                duration == that.duration &&
                effects.equals(that.effects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, duration, effects);
    }

    @Override
    public DefaultEffectFrame clone() {
        DefaultEffectFrame clone = (DefaultEffectFrame) super.clone();
        clone.appliedGameEffects = Collections.newSetFromMap(new IdentityHashMap<>());
        return clone;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.id = in.readInt();
        this.duration = in.readInt();  //only rendering-relevant variables will be serialized
    }

    private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException {
        out.writeInt(id);
        out.writeInt(duration);
    }
}
