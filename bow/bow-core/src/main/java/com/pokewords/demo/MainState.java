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
import com.pokewords.framework.sprites.Sprite;

import java.awt.event.KeyEvent;

import static com.pokewords.demo.TestingApplication.Sprites.*;

public class MainState extends EmptyAppState {
    private Sprite currentSprite;
    private Sprite[] sprites;

    @Override
    protected AppStateWorld onCreateAppStateWorld(GameEngineFacade gameEngineFacade) {
        return new ContainerAppStateWorld(this, 1104, 828);
    }

    @Override
    protected void onAppStateEntering() {
        getGameWindowsConfigurator().gameSize(1104, 828);

        getAppStateWorld().spawn(getGameEngineFacade().createSpriteBoard(92)
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

        sprites = new Sprite[]{
                createSprite(HERO),
                createSprite(SNOWBALL_EX),
                createSprite(SNIPER_TANK),
                createSprite(BALL),
                createSprite(RIFLETANK),
                createSprite(SNOWBALL),
                createSprite(BLACKBOSS),
                createSprite(BLACKGUNNER),
                createSprite(EVIL)
        };

        getAppStateWorld().spawn(currentSprite = sprites[0]);

        bindKeyClickedAction(key -> {
            switch (key)
            {
                case KeyEvent.VK_0:
                    operateSprite(0);
                    break;
                case KeyEvent.VK_1:
                    operateSprite(1);
                    break;
                case KeyEvent.VK_2:
                    operateSprite(2);
                    break;
                case KeyEvent.VK_3:
                    operateSprite(3);
                    break;
                case KeyEvent.VK_4:
                    operateSprite(4);
                    break;
                case KeyEvent.VK_5:
                    operateSprite(5);
                    break;
                case KeyEvent.VK_6:
                    operateSprite(6);
                    break;
                case KeyEvent.VK_7:
                    operateSprite(7);
                    break;
                case KeyEvent.VK_8:
                    operateSprite(8);
                    break;
                case KeyEvent.VK_9:
                    operateSprite(9);
                    break;
            }
        } );
    }

    private void operateSprite(int index) {
        try {
            getAppStateWorld().spawn(sprites[index]);
            getAppStateWorld().removeSprite(currentSprite);
            currentSprite = sprites[index];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            // not perform
        }
    }
}
