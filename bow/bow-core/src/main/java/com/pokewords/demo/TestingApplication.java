/*
 * Copyright (c) 2018 WaterBall
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.pokewords.demo;

import com.pokewords.components.MovingKeyListenerComponent;
import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.ioc.IocContainer;
import com.pokewords.framework.ioc.ReleaseIocContainer;
import com.pokewords.framework.sprites.components.ImageComponent;
import com.pokewords.framework.sprites.components.RigidBodyComponent;
import com.pokewords.framework.sprites.components.frames.ImageFrameFactory;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.GameApplication;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;

import java.awt.*;

import static com.pokewords.demo.TestingApplication.Sprites.*;

public class TestingApplication extends GameApplication {

    public TestingApplication(IocContainer iocContainer) {
        super(iocContainer);
    }

    public enum Sprites {
        GRASS, TREE, WATER,
        CHARACTER,
        RIFLETANK, SNIPER_TANK, BALL, SNOWBALL, SNOWBALL_EX,
        BLACKBOSS, BLACKGUNNER, EVIL, HERO
    }


    @Override
    protected void onGameWindowsConfiguration(GameWindowsConfigurator gameWindowsConfigurator) {
        gameWindowsConfigurator
                .name("Basic App demo")
                .atCenter();
    }

    @Override
    protected void onSpriteDeclaration(SpriteInitializer spriteInitializer) {
        spriteInitializer.declare(GRASS)
                        .with(new ImageComponent(
                                ImageFrameFactory.fromPath(0, "assets/pics/terrain/grass.png")))
                        .commit();

        spriteInitializer.declare(TREE)
                .with(new ImageComponent(
                        ImageFrameFactory.fromPath(0, "assets/pics/terrain/tree.png")))
                .with(RigidBodyComponent.getInstance())
                .commit();

        spriteInitializer.declare(WATER)
                .with(new ImageComponent(
                        ImageFrameFactory.fromPath(0, "assets/pics/terrain/water.png")))
                .with(RigidBodyComponent.getInstance())
                .commit();

        spriteInitializer.declare(CHARACTER)
                .with(RigidBodyComponent.withIgnoredTypes(CHARACTER))
                .with(new MovingKeyListenerComponent())
                .commit();

        spriteInitializer
                .declareFromParent(CHARACTER, RIFLETANK)
                .position(new Point(80, 184))
                .with("assets/scripts/RifleTank.bow")
                .commit();

        spriteInitializer
                .declareFromParent(CHARACTER, SNIPER_TANK)
                .position(new Point(240, 184))
                .with("assets/scripts/SniperTank.bow")
                .areaSize(75, 75)
                .commit();


        spriteInitializer
                .declareFromParent(CHARACTER, BALL)
                .position(new Point(400, 184))
                .with("assets/scripts/Ball.bow")
                .areaSize(70, 70)
                .commit();

        spriteInitializer
                .declareFromParent(CHARACTER, SNOWBALL)
                .position(new Point(560, 184))
                .with("assets/scripts/SnowBall.bow")
                .areaSize(70, 70)
                .commit();

        spriteInitializer
                .declareFromParent(CHARACTER, SNOWBALL_EX)
                .position(new Point(640, 184))
                .with("assets/scripts/SnowBallEx.bow")
                .areaSize(70, 70)
                .commit();

        // Black script
        spriteInitializer
                .declareFromParent(CHARACTER, BLACKBOSS)
                .position(new Point(720, 184))
                .with("assets/scripts/blackBoss.bow")
                .areaSize(53, 82)
                .commit();

        spriteInitializer
                .declareFromParent(CHARACTER, BLACKGUNNER)
                .position(new Point(880, 184))
                .with("assets/scripts/blackGunner.bow")
                .areaSize(53, 79)
                .commit();

        spriteInitializer
                .declareFromParent(CHARACTER, EVIL)
                .position(new Point(480, 184))
                .with("assets/scripts/evil.bow")
                .areaSize(62, 72)
                .commit();

        spriteInitializer
                .declareFromParent(CHARACTER, HERO)
                .position(new Point(184, 736))
                .with("assets/scripts/Hero.bow")
                .commit();
    }

    @Override
    protected void onAppStatesConfiguration(AppStateMachine asm) {
        MainState mainState = asm.createState(MainState.class);
        asm.setGameInitialState(mainState);
    }


    public static void main(String[] args) {
        TestingApplication app = new TestingApplication(new ReleaseIocContainer());
        app.launch();
    }
}
