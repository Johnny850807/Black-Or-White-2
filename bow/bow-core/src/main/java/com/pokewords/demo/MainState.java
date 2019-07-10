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


import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.engine.asm.states.EmptyAppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.engine.gameworlds.ContainerAppStateWorld;

import static com.pokewords.demo.DemoApplication.Sprites.*;

public class MainState extends EmptyAppState {

    @Override
    protected AppStateWorld onCreateAppStateWorld(GameEngineFacade gameEngineFacade) {
        return new ContainerAppStateWorld(this, 960, 720);
    }

    @Override
    protected void onAppStateEntering() {
        getGameWindowsConfigurator().gameSize(960, 720);
        getAppStateWorld().spawn(getGameEngineFacade().createSpriteBoard(80)
                                .symbol('*', TREE)
                                .symbol('-', GRASS)
                                .symbol('0', WATER)
                .board(new String[]{    /*0*/     "**-**--**-**",
                                        /*1*/     "*---*--*---*",
                                        /*2*/     "------------",
                                        /*3*/     "*--0*--*0--*",
                                        /*4*/     "*--0----0--*",
                                        /*5*/     "*----**----*",
                                        /*6*/     "***------***",
                                        /*7*/     "*----------*",
                                        /*8*/     "**-******-**"})
                                .build());

        getAppStateWorld().spawn(createSprite(SNOWBALEX));
        getAppStateWorld().spawn(createSprite(SNIPERTANK));
        getAppStateWorld().spawn(createSprite(BALL));
        getAppStateWorld().spawn(createSprite(RIFLETANK));
        getAppStateWorld().spawn(createSprite(SNOWBALL));
        getAppStateWorld().spawn(createSprite(BLACKBOSS));
        getAppStateWorld().spawn(createSprite(BLACKGUNNER));
        getAppStateWorld().spawn(createSprite(EVIL));
    }
}
