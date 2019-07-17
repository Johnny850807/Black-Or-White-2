package com.pokewords;

import com.pokewords.appstates.GameState;
import com.pokewords.appstates.MenuAppState;
import com.pokewords.appstates.MyMultiplayerRoomState;
import com.pokewords.components.*;
import com.pokewords.components.menus.MenuButtonMouseListener;
import com.pokewords.constants.AsmEvents;
import com.pokewords.constants.SoundTypes;
import com.pokewords.constants.SpriteTypes;
import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.ioc.IocContainer;
import com.pokewords.framework.ioc.ReleaseIocContainer;
import com.pokewords.framework.sprites.components.ImageComponent;
import com.pokewords.framework.sprites.components.RigidBodyComponent;
import com.pokewords.framework.sprites.components.frames.ImageFrame;
import com.pokewords.framework.sprites.components.frames.ImageFrameFactory;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.GameApplication;
import com.pokewords.framework.views.SoundPlayer;
import com.pokewords.framework.views.effects.AppStateTransitionEffect;
import com.pokewords.framework.views.effects.CrossFadingTransitionEffect;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;
import com.pokewords.weaving.BlackOrWhiteWeaverNode;

public class BlackOrWhite extends GameApplication {

    public BlackOrWhite(IocContainer iocContainer) {
        super(iocContainer);
    }

    @Override
    protected void onGameWindowsConfiguration(GameWindowsConfigurator gameWindowsConfigurator) {
        gameWindowsConfigurator.name("Black Or White")
                        .atCenter();
    }

    @Override
    protected void onSoundPlayerConfiguration(SoundPlayer soundPlayer) {
        soundPlayer.addSound(SoundTypes.OPENING, SoundTypes.OPENING.getSoundPath());
    }

    @Override
    protected void onSpriteDeclaration(SpriteInitializer spriteInitializer) {
        declareRootType(spriteInitializer);
        declareForMenuState(spriteInitializer);
        declareForGameState(spriteInitializer);
    }

    private void declareRootType(SpriteInitializer spriteInitializer) {
        spriteInitializer.declare(SpriteTypes.ROOT)
                .weaver(new BlackOrWhiteWeaverNode())
                .commit();
    }

    private void declareForMenuState(SpriteInitializer spriteInitializer) {
        spriteInitializer.declare(SpriteTypes.MENU_PLAY_BUTTON)
                .with(new ImageComponent(ImageFrameFactory.fromPath(0, "assets/pics/menu/bluePlayButton.png")
                                            .flags(ImageFrame.CANVAS_FLAG_RENDER_BY_CENTER)))
                    .with(new MenuButtonMouseListener("PlayButton"))
                    .area(400, 236, 97, 65)
                    .commit();

        spriteInitializer.declare(SpriteTypes.MENU_MULTIPLAYER_BUTTON)
                .with(new ImageComponent(ImageFrameFactory.fromPath(0, "assets/pics/menu/blueMultiplayerButton.png")
                        .flags(ImageFrame.CANVAS_FLAG_RENDER_BY_CENTER)))
                .with(new MenuButtonMouseListener("MultiplayerButton"))
                .area(400, 436, 97, 65)
                .commit();
    }

    private void declareForGameState(SpriteInitializer spriteInitializer) {
        declareForGameLayout(spriteInitializer);
        declareGameSprites(spriteInitializer);
    }

    private void declareForGameLayout(SpriteInitializer spriteInitializer) {

    }

    private void declareGameSprites(SpriteInitializer spriteInitializer) {
        declareCharacters(spriteInitializer);
        declareBullets(spriteInitializer);
        declarePickables(spriteInitializer);
    }

    private void declareCharacters(SpriteInitializer spriteInitializer) {
        spriteInitializer.declareFromParent(SpriteTypes.ROOT, SpriteTypes.CHARACTER)
                .with(new CharacterComponent())
                .commit();

        spriteInitializer.declareFromParent(SpriteTypes.CHARACTER, SpriteTypes.HERO)
            .with(RigidBodyComponent.getInstance())
            .commit();

        spriteInitializer.declareFromParent(SpriteTypes.CHARACTER, SpriteTypes.MONSTER)
            .commit();
}

    private void declareBullets(SpriteInitializer spriteInitializer) {
        spriteInitializer.declareFromParent(SpriteTypes.ROOT, SpriteTypes.BULLET)
                .with(new BulletCollisionListenerComponent())
                .commit();

        spriteInitializer.declareFromParent(SpriteTypes.BULLET, SpriteTypes.BULLET_BLUE_PELLET)
                .with("assets/scripts/BluePellet.bow")
                .commit();
    }

    private void declarePickables(SpriteInitializer spriteInitializer) {
        spriteInitializer.declareFromParent(SpriteTypes.ROOT, SpriteTypes.PICKABLE).commit();
        spriteInitializer.declareFromParent(SpriteTypes.PICKABLE, SpriteTypes.PICKABLE_ITEM).commit();
        spriteInitializer.declareFromParent(SpriteTypes.PICKABLE, SpriteTypes.PICKABLE_GUN).commit();

        spriteInitializer.declareFromParent(SpriteTypes.PICKABLE_GUN, SpriteTypes.PICKABLE_MACHINE_GUN)
                .with(new MachineGunComponent())
                .commit();

        spriteInitializer.declareFromParent(SpriteTypes.PICKABLE_GUN, SpriteTypes.PICKABLE_PISTOL)
                .with(new PistolComponent())
                .commit();

        spriteInitializer.declareFromParent(SpriteTypes.PICKABLE_GUN, SpriteTypes.PICKABLE_SNIPER_RIFLE)
                .with(new SniperRifleComponent())
                .commit();
    }

    @Override
    protected void onAppStatesConfiguration(AppStateMachine asm) {
        MenuAppState menuAppState = asm.createState(MenuAppState.class);
        asm.setGameInitialState(menuAppState);
        GameState gameState = asm.createState(GameState.class);
        asm.addTransition(menuAppState, AsmEvents.GAME_START, gameState, new CrossFadingTransitionEffect());

        MyMultiplayerRoomState multiplayerRoomState = asm.createState(MyMultiplayerRoomState.class);
        asm.addTransition(menuAppState, AsmEvents.TO_MULTIPLAYER, multiplayerRoomState, new CrossFadingTransitionEffect());
    }


    public static void main(String[] args) {
        new BlackOrWhite(new ReleaseIocContainer()).launch();
    }
}
