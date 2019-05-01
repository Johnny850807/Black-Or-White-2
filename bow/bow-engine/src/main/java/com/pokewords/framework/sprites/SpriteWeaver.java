package com.pokewords.framework.sprites;

import com.pokewords.framework.engine.exceptions.SpriteBuilderException;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;
import com.pokewords.framework.sprites.parsing.Script;

import java.util.LinkedList;
import java.util.function.BiConsumer;

public class SpriteWeaver {
    private LinkedList<BiConsumer<Script, Sprite>> weaverNodes;
    private IocFactory iocFactory;
    private FrameStateMachineComponent fsmComponent;
    private PropertiesComponent propertiesComponent;
    private Sprite sprite;

    public SpriteWeaver(IocFactory iocFactory, Sprite sprite) {
        weaverNodes = new LinkedList<>();
        this.iocFactory = iocFactory;
        this.sprite = sprite;
    }

    public void addNode(BiConsumer<Script, Sprite> node) {
        weaverNodes.add(node);
    }

    public void weave() {

    }

    public void setIocFactory(IocFactory iocFactory) {
        this.iocFactory = iocFactory;
    }

    public void setFSMComponent(FrameStateMachineComponent fsmComponent) {
        this.fsmComponent = fsmComponent;
    }

    public void setPropertiesComponent(PropertiesComponent propertiesComponent) {
        this.propertiesComponent = propertiesComponent;
    }

    public void addComponent(String name, Component component) {
        validateComponentName(name);
        sprite.putComponent(name, component);
    }

    private void validateComponentName(String name) {
        if (sprite.getComponentByName(name).isPresent()) {
            throw new SpriteBuilderException("Duplicate component name is not allowed.");
        }
    }

    private void validateSpriteMandatoryComponents() {
        if (fsmComponent == null) {
            throw new SpriteBuilderException(
                    "FrameStateMachineComponent is required, use setupParser() to create it");
        }
        if (propertiesComponent == null) {
            throw new SpriteBuilderException(
                    "PropertiesComponent is required, use setPropertiesComponent() to create it");
        }
    }
}
