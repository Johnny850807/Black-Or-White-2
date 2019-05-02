package com.pokewords.framework.sprites;

import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;
import com.pokewords.framework.sprites.parsing.Script;

import java.util.function.BiConsumer;

/**
 * @author nyngwang
 */
public interface SpriteBuilder {

    SpriteBuilder init();

    SpriteBuilder setFSMComponent(FrameStateMachineComponent fsmComponent);

    SpriteBuilder setPropertiesComponent(PropertiesComponent propertiesComponent);

    SpriteBuilder addComponent(String name, Component component);

    SpriteBuilder buildScriptFromPath(String path);

    SpriteBuilder setScript(Script script);

    SpriteBuilder addWeaverNode(BiConsumer<Script, Sprite> node);

    Sprite build();
}
