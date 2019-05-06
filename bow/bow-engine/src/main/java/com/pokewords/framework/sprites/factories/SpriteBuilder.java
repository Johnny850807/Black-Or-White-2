package com.pokewords.framework.sprites.factories;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;
import com.pokewords.framework.sprites.parsing.Script;

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

    SpriteBuilder addWeaverNode(SpriteWeaver.Node node);

    Sprite build();
}
