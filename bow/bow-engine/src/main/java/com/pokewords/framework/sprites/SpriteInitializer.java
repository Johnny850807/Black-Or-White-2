package com.pokewords.framework.sprites;

import com.pokewords.framework.engine.exceptions.GameEngineException;
import com.pokewords.framework.engine.exceptions.SpriteDeclaratorException;
import com.pokewords.framework.engine.utils.StringUtility;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.ioc.ReleaseIocFactory;
import com.pokewords.framework.sprites.components.*;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.parsing.Element;
import com.pokewords.framework.sprites.parsing.Script;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

/**
 * This is the direct api to the client which is a convenient way to to declare and init the Sprites.
 * You can set different SpriteInitializer#InitializationMode to customize your init pattern.
 * @author johnny850807 (waterball)
 */
public class SpriteInitializer {
    private PrototypeFactory prototypeFactory;
    private SpriteBuilder spriteBuilder;
    private InitializationMode initializationMode = InitializationMode.LAZY;

    // <sprite's name, the sprite>
    private Map<String, Declaration> declarationMap = new IdentityHashMap<>();

    public enum InitializationMode {
        /**
         * The sprite declarator will init sprites only during the first time it's requested to be created.
         */
        LAZY,

        /**
         * The sprite declarator will init sprites once you commit your declarator.
         */
        NON_LAZY,

        /**
         * The sprite declarator will not init sprites at any time, it's waiting for your invocation of the method SpriteInitializer#init(type),
         * the sprite is init only when you invoke that method. So you can customize when to init, and in which AppState. Notice, anytime, if a sprite
         * you are going to create has not been init, it will help you init then create it. If you want to strictly expect all inits are by yourself,
         * and raise an explicit error message at such situation, use CUSTOM_STRICT instead.
         */
        CUSTOM,

        /**
         * The sprite declarator will not init sprites at any time, it's waiting for your invocation of the method SpriteInitializer#init(type),
         * the sprite is init only when you invoke that method. So you can customize when to init, and in which AppState. Notice, anytime, a sprite
         * you are going to create has not been init, this will throw the GameEngineException. If you want this to help you init rather than error,
         * use CUSTOM instead.
         */
        CUSTOM_STRICT
    }

    // <sprite's name, has the sprite been init>, this needn't be used under NON_LAZY mode.
    private Map<String, Boolean> spriteHasBeenInitMap;

    public SpriteInitializer(IocFactory iocFactory) {
        this.prototypeFactory = iocFactory.prototypeFactory();
        this.spriteBuilder = iocFactory.spriteBuilder();
    }

    /**
     * Set the initialization mode, it's InitializationMode#LAZY as default.
     */
    public void setInitializationMode(InitializationMode initializationMode) {
        this.initializationMode = initializationMode;

        if (initializationMode != InitializationMode.NON_LAZY && spriteHasBeenInitMap == null)
            spriteHasBeenInitMap = new HashMap<>();
    }

    public boolean hasSpriteBeenInit(String type) {
        return initializationMode == InitializationMode.NON_LAZY ? true : spriteHasBeenInitMap.get(type);
    }

    public InitializationMode getInitializationMode() {
        return initializationMode;
    }

    public SpriteDeclarator declare(@NotNull String type) {
        return new SpriteDeclarator(type);
    }

    public class SpriteDeclarator {
        private String type;
        private Declaration declaration;

        protected SpriteDeclarator(String type) {
            this.type = type;
            this.declaration = new Declaration(type);
        }

        public SpriteDeclarator with(@NotNull String componentName, @NotNull Component component) {
            declaration.componentMap.put(componentName, component);
            return this;
        }

        public SpriteDeclarator with(@NotNull String scriptPath) {
            declaration.scriptPath = scriptPath;
            return this;
        }

        public SpriteDeclarator with(@NotNull FrameStateMachineComponent fsmComp) {
            declaration.frameStateMachineComponent = fsmComp;
            return this;
        }

        public SpriteDeclarator with(@NotNull PropertiesComponent propComp) {
            declaration.propertiesComponent = propComp;
            return this;
        }

        public SpriteDeclarator collidable() {
            declaration.componentMap.put(Component.COLLIDABLE, CollidableComponent.getInstance());
            return this;
        }

        public SpriteDeclarator clickable(ClickableComponent clickableComponent) {
            declaration.componentMap.put(Component.CLICKABLE, clickableComponent);
            return this;
        }

        public SpriteDeclarator position(int x, int y) {
            declaration.propertiesComponent.setPosition(x, y);
            return this;
        }

        public SpriteDeclarator position(@NotNull Point point) {
            declaration.propertiesComponent.setPosition(point);
            return this;
        }

        public SpriteDeclarator with(@NotNull Script script) {
            declaration.script = script;
            return this;
        }

        public SpriteDeclarator weaver(@NotNull SpriteWeaver.Node weaverNode) {
            declaration.weaverNodes.add(weaverNode);
            return this;
        }

        public SpriteCreator commit() throws SpriteDeclaratorException {
            validateDeclarations();

            if (initializationMode == InitializationMode.NON_LAZY)  //non-lazy mode needn't save the declarations, simply init it after declare it
                declaration.startInitializingSprite();
            else
                declarationMap.put(type, declaration);

            return new SpriteCreator();
        }

        public class SpriteCreator {
            public Sprite create() {
                return createSprite(type);
            }
        }

        private void validateDeclarations() throws SpriteDeclaratorException {
            validatePropertiesComponentSet();
            validateFrameStateMachineComponentSet();
        }

        private void validatePropertiesComponentSet() throws SpriteDeclaratorException {
            if (declaration.propertiesComponent == null)
                throw new SpriteDeclaratorException(String.format("Error occurs during declaring the sprite '%s', " +
                        "'PropertiesComponent' should not be set (or it should not be null).", type));

            if (!declaration.propertiesComponent.getType().equals(type))
                throw new SpriteDeclaratorException(String.format("Error occurs during declaring the sprite '%s', " +
                        "your propertiesComponent's type given is %s, but your sprite's type is declared as %s.",
                        type, declaration.propertiesComponent.getType(), type));
        }

        private void validateFrameStateMachineComponentSet() throws SpriteDeclaratorException {
            if (declaration.script == null && StringUtility.isNullOrEmpty(declaration.scriptPath) && declaration.frameStateMachineComponent == null)
                throw new SpriteDeclaratorException(
                        String.format("Error occurs during declaring the sprite '%s', you should set the scriptPath of the sprite '%s'. " +
                                "(Alternatively, you can set the script instance or directly the FrameStateMachineComponent instance.)",
                                type, type));

            if (Files.notExists(Paths.get(declaration.scriptPath)))
                throw new SpriteDeclaratorException(String.format("Error occurs during declaring the sprite '%s', the scriptPath '%s' does not exist.", type, declaration.scriptPath));
        }
    }

    /**
     * Convenient createSprite(type) method for allowing you passing your own type enum in.
     * @param type the created sprite's name (in the form of some of your enum)
     * @return the sprite created
     * @throws GameEngineException
     */
    public Sprite createSprite(Object type) throws GameEngineException {
        return createSprite(type.toString());
    }

    /**
     * @param type the created sprite's name
     * @return the sprite created
     * @throws GameEngineException
     */
    public Sprite createSprite(String type) throws GameEngineException {
        validateSpriteHasBeenDeclared(type);

        if (!hasSpriteBeenInit(type))
        {
            validateUnderCustomStrictModeShouldInitByYourself(type);
            declarationMap.get(type).startInitializingSprite();
            spriteHasBeenInitMap.put(type, true);
        }

        return prototypeFactory.cloneSprite(type);
    }


    private void validateSpriteHasBeenDeclared(String spriteName) {
        if (!declarationMap.containsKey(spriteName))
            throw new SpriteDeclaratorException(String.format("You haven't declared the sprite '%s', " +
                    "use declare(type) to start your declarations.", spriteName));
    }

    private void validateUnderCustomStrictModeShouldInitByYourself(String spriteName) {
        if (initializationMode == InitializationMode.CUSTOM_STRICT)
            throw new GameEngineException(String.format("You are under CUSTOM_STRICT init mode, so you should init all sprites on your own, " +
                    "the sprite '%s' has not been init.", spriteName));
    }


    /**
     * This is the method only used under the CUSTOM (or CUSTOM_STRICT)init mode, under CUSTOM (or CUSTOM_STRICT) mode, use this method to init the sprite.
     * @see SpriteInitializer#setInitializationMode(InitializationMode)
     * @param type the init sprite's name
     */
    public void initSprite(String type) throws GameEngineException {
        validateOnlyCustomInitModeCanUseThisMethod();
        validateSpriteHasBeenDeclared(type);

        if (!hasSpriteBeenInit(type))
            declarationMap.get(type).startInitializingSprite();
    }

    private void validateOnlyCustomInitModeCanUseThisMethod() {
        if (initializationMode != InitializationMode.CUSTOM && initializationMode != InitializationMode.CUSTOM_STRICT)
            throw new GameEngineException(String.format("You are invoking initSprite(type) under %s mode, " +
                    "you should set the initialization mode to the CUSTOM mode to enable this method.", initializationMode));
    }

    private class Declaration {
        String type;
        PropertiesComponent propertiesComponent;
        FrameStateMachineComponent frameStateMachineComponent;
        Map<String, Component> componentMap = new HashMap<>();

        String scriptPath = "";
        Script script;  // use script rather than scriptPath iff script != null

        Set<SpriteWeaver.Node> weaverNodes = new LinkedHashSet<>();

        public Declaration(String type) {
            this.type = type;
            this.propertiesComponent = new PropertiesComponent(type);
        }

        protected void startInitializingSprite() {
            setFrameStateMachineComponent();
            spriteBuilder.setPropertiesComponent(propertiesComponent);
            componentMap.forEach(spriteBuilder::addComponent);
            weaverNodes.forEach(spriteBuilder::addWeaverNode);
            prototypeFactory.addPrototype(type, spriteBuilder.build());
        }

        private void setFrameStateMachineComponent() {
            if (frameStateMachineComponent != null)
                spriteBuilder.setFSMComponent(frameStateMachineComponent);
            else if (script != null)
                spriteBuilder.setScript(script);
            else
                spriteBuilder.buildScriptFromPath(scriptPath);
        }
    }


    public static void main(String[] args) {
        SpriteInitializer spriteInitializer = new SpriteInitializer(new ReleaseIocFactory());

        Sprite hero = spriteInitializer.declare("hero")
                        .with("path/to/script.bow")
                        .position(250, 250)
                        .collidable()
                        .weaver((script, sprite) -> { /*weaving*/})
                        .commit()
                        .create();
    }
}


