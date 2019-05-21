package com.pokewords.framework.sprites.factories;

import com.pokewords.framework.engine.exceptions.SpriteDeclarationException;
import com.pokewords.framework.commons.utils.Resources;
import com.pokewords.framework.commons.utils.StringUtility;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.PropertiesComponent;
import com.pokewords.framework.sprites.parsing.Script;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.nio.file.Files;
import java.util.List;
import java.util.*;

/**
 * This is the direct api to the client which is a convenient way to declare and init the Sprites.
 * You can set different SpriteInitializer#InitializationMode to customize the init pattern.
 *
 * @author johnny850807 (waterball)
 */
public class SpriteInitializer {
    private PrototypeFactory prototypeFactory;
    private SpriteBuilder spriteBuilder;
    private InitializationMode initializationMode;

    // <sprite's type, the sprite>
    private final Map<Object, Declaration> declarationMap = new HashMap<>();

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

    private final HashSet<Object> spriteTypesThatHaveBeenInit = new HashSet<>();

    public SpriteInitializer(IocFactory iocFactory) {
        this.prototypeFactory = iocFactory.prototypeFactory();
        this.spriteBuilder = iocFactory.spriteBuilder();
    }

    /**
     * Set the initialization mode, it's InitializationMode#LAZY as default.
     */
    public void setInitializationMode(InitializationMode initializationMode) {
        this.initializationMode = initializationMode;
    }

    public boolean hasSpriteBeenInit(Object type) {
        return initializationMode == InitializationMode.NON_LAZY || spriteTypesThatHaveBeenInit.contains(type);
    }

    public InitializationMode getInitializationMode() {
        return initializationMode;
    }


    public SpriteDeclarator declare(@NotNull Object type) {
        return new SpriteDeclarator(type);
    }


    public class SpriteDeclarator {
        private Object type;
        private Declaration declaration;

        protected SpriteDeclarator(Object type) {
            this.type = type;
            this.declaration = new Declaration(type);
        }

        public SpriteDeclarator with(@NotNull Component component) {
            declaration.components.add(component);
            return this;
        }

        public SpriteDeclarator with(@NotNull Script script) {
            declaration.script = script;
            return this;
        }

        public SpriteDeclarator with(@NotNull String scriptPath) {
            declaration.scriptPath = scriptPath;
            return this;
        }

        public SpriteDeclarator with(@NotNull PropertiesComponent propertiesComponent) {
            declaration.propertiesComponent = propertiesComponent;
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

        public SpriteDeclarator areaSize(Dimension size) {
            return areaSize((int) size.getWidth(), (int) size.getHeight());
        }

        public SpriteDeclarator areaSize(int w, int h) {
            declaration.propertiesComponent.getArea().setSize(w, h);
            return this;
        }
        public SpriteDeclarator area(Rectangle area) {
            declaration.propertiesComponent.setArea(area);
            return this;
        }

        public SpriteDeclarator area(int x, int y, int w, int h) {
            declaration.propertiesComponent.setArea(x, y, w, h);
            return this;
        }

        public SpriteDeclarator body(Rectangle body) {
            declaration.propertiesComponent.setBody(body);
            return this;
        }

        public SpriteDeclarator body(int x, int y, int w, int h) {
            declaration.propertiesComponent.setBody(x, y, w, h);
            return this;
        }

        public SpriteDeclarator weaver(@NotNull SpriteWeaver.Node weaverNode) {
            declaration.weaverNodes.add(weaverNode);
            return this;
        }

        public SpriteCreator commit() throws SpriteDeclarationException {
            validateDeclarations();

            if (initializationMode == InitializationMode.NON_LAZY)  //non-lazy mode needn't save the declarations, simply init it after declare it
                declaration.startInitializingSprite();

            declarationMap.put(type, declaration);

            return new SpriteCreator();
        }

        public class SpriteCreator {
            public Sprite create() {
                return createSprite(type);
            }
        }

        private void validateDeclarations() throws SpriteDeclarationException {
            validatePropertiesComponentSet();
            validateScriptPathIfNotNull();
        }


        private void validatePropertiesComponentSet() throws SpriteDeclarationException {
            if (declaration.propertiesComponent == null)
                throw new SpriteDeclarationException(String.format("Error occurs during declaring the sprite '%s', " +
                        "'PropertiesComponent' should not be set (or it should not be null).", type));

            if (!declaration.propertiesComponent.getType().equals(type))
                throw new SpriteDeclarationException(String.format("Error occurs during declaring the sprite '%s', " +
                                "your propertiesComponent's type is '%s', but your sprite's type is declared '%s'.",
                        type, declaration.propertiesComponent.getType(), type));
        }

        private void validateScriptPathIfNotNull() throws SpriteDeclarationException {
            if (!StringUtility.isNullOrEmpty(declaration.scriptPath) &&
                    Files.notExists(Resources.get(declaration.scriptPath).toPath()))
                throw new SpriteDeclarationException(String.format("Error occurs during declaring the sprite '%s', the scriptPath '%s' does not exist.", type, declaration.scriptPath));
        }
    }


    /**
     * @param type the created sprite's name
     * @return the sprite created
     * @throws SpriteDeclarationException
     */
    public Sprite createSprite(Object type) throws SpriteDeclarationException {
        validateSpriteHasBeenDeclared(type);

        if (!hasSpriteBeenInit(type)) {
            validateUnderCustomStrictModeShouldInitByYourself(type);
            declarationMap.get(type).startInitializingSprite();
            spriteTypesThatHaveBeenInit.add(type);
        }

        return prototypeFactory.cloneSprite(type);
    }


    private void validateSpriteHasBeenDeclared(Object type) {
        if (!hasDeclared(type)) {
            System.out.println(StringUtility.toString(declarationMap));
            throw new SpriteDeclarationException(String.format("You haven't declared the sprite '%s', " +
                    "use declare(type) to start your declarations. (Did you commit your declaration?)", type));
        }
    }

    public boolean hasDeclared(Object type) {
        return declarationMap.containsKey(type);
    }

    private void validateUnderCustomStrictModeShouldInitByYourself(Object type) {
        if (initializationMode == InitializationMode.CUSTOM_STRICT)
            throw new SpriteDeclarationException(String.format("You are under CUSTOM_STRICT init mode, so you should init all sprites on your own, " +
                    "the sprite '%s' has not been init.", type));
    }


    /**
     * This is the method only used under the CUSTOM (or CUSTOM_STRICT)init mode, under CUSTOM (or CUSTOM_STRICT) mode, use this method to init the sprite.
     *
     * @param type the init sprite's name
     * @see SpriteInitializer#setInitializationMode(InitializationMode)
     */
    public void initSprite(String type) throws SpriteDeclarationException {
        validateOnlyCustomInitModeCanUseThisMethod();
        validateSpriteHasBeenDeclared(type);

        if (!hasSpriteBeenInit(type)) {
            declarationMap.get(type).startInitializingSprite();
            spriteTypesThatHaveBeenInit.add(type);
        }
    }

    private void validateOnlyCustomInitModeCanUseThisMethod() {
        if (initializationMode != InitializationMode.CUSTOM && initializationMode != InitializationMode.CUSTOM_STRICT)
            throw new SpriteDeclarationException(String.format("You are invoking initSprite(type) under %s mode, " +
                    "you should set the initialization mode to the CUSTOM mode to enable this method.", initializationMode));
    }

    private class Declaration {
        Object type;
        PropertiesComponent propertiesComponent;
        HashSet<Component> components = new HashSet<>();

        Script script;
        String scriptPath;

        List<SpriteWeaver.Node> weaverNodes = new LinkedList<>();

        public Declaration(Object type) {
            this.type = type;
            this.propertiesComponent = new PropertiesComponent(type);
        }

        protected void startInitializingSprite() {
            spriteBuilder.init();
            spriteBuilder.setPropertiesComponent(propertiesComponent);
            setFrameStateMachineComponent();
            components.forEach(spriteBuilder::addComponent);
            weaverNodes.forEach(spriteBuilder::addWeaverNode);
            Sprite sprite = spriteBuilder.build();
            prototypeFactory.addPrototype(type, sprite);
        }

        private void setFrameStateMachineComponent() {
            if (script != null)
                spriteBuilder.setScript(script);
            else if (scriptPath != null)
                spriteBuilder.buildScriptFromPath(scriptPath);
        }
    }

}


