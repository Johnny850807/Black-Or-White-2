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

package com.pokewords.Demo;


import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.engine.asm.states.EmptyAppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.engine.gameworlds.ContainerAppStateWorld;

public class MainState extends EmptyAppState {

    enum Sprites {
        RIFLETANK, SNIPERTANK, BALL, SNOWBALL, SNOWBALEX
    }


    @Override
    protected AppStateWorld onCreateAppStateWorld(GameEngineFacade gameEngineFacade) {
        return new ContainerAppStateWorld(this);
    }

    @Override
    protected void onAppStateCreating(AppStateWorld world) {
        getGameWindowsConfigurator().gameSize(800, 600);
        world.spawn(createSprite(Sprites.SNOWBALEX));
        world.spawn(createSprite(Sprites.SNIPERTANK));
        world.spawn(createSprite(Sprites.BALL));
        world.spawn(createSprite(Sprites.RIFLETANK));
        world.spawn(createSprite(Sprites.SNOWBALL));
    }
}
