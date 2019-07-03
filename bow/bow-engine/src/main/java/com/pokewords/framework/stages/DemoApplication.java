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

package shawnDemo;

import basics.PlayerKeyListenerComponent;
import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.ioc.IocContainer;
import com.pokewords.framework.ioc.ReleaseIocContainer;
import com.pokewords.framework.sprites.components.RigidBodyComponent;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.GameApplication;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;

import java.awt.*;

public class DemoApplication extends GameApplication {

    public DemoApplication(IocContainer iocContainer) {
        super(iocContainer);
    }

    @Override
    protected void onGameWindowsConfiguration(GameWindowsConfigurator gameWindowsConfigurator) {
        gameWindowsConfigurator
                .name("Basic App Demo")
                .atCenter();
    }

    @Override
    protected void onSpriteDeclaration(SpriteInitializer spriteInitializer) {
        spriteInitializer
                .declare(MainState.Sprites.RIFLETANK)
                .position(new Point(100, 200))
                .with("scripts/tank/SnowBallEx.bow")
                .with(RigidBodyComponent.getInstance())
                .with(new PlayerKeyListenerComponent())
                .areaSize(70, 70)
                .commit();

        spriteInitializer
                .declare(MainState.Sprites.SNIPERTANK)
                .position(new Point(250, 200))
                .with("scripts/tank/SniperTank.bow")
                .with(RigidBodyComponent.getInstance())
                .with(new PlayerKeyListenerComponent())
                .areaSize(75, 75)
                .commit();


        spriteInitializer
                .declare(MainState.Sprites.BALL)
                .position(new Point(400, 200))
                .with("scripts/tank/Ball.bow")
                .with(RigidBodyComponent.getInstance())
                .with(new PlayerKeyListenerComponent())
                .areaSize(70, 70)
                .commit();


    }

    @Override
    protected void onAppStatesConfiguration(AppStateMachine asm) {
        MainState mainState = asm.createState(MainState.class);
        asm.setGameInitialState(mainState);
    }


    public static void main(String[] args) {
        DemoApplication app = new DemoApplication(new ReleaseIocContainer());
        app.launch();
    }
}
