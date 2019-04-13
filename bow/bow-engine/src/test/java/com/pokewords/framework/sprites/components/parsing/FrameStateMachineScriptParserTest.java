package com.pokewords.framework.sprites.components.parsing;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.parsing.FrameSegment;
import com.pokewords.framework.sprites.parsing.FrameStateMachineScriptParser;
import org.junit.Test;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class FrameStateMachineScriptParserTest extends AbstractTest {
    FrameStateMachineScriptParser parser = iocFactory.frameStateMachineScriptParser();
    final String SCRIPT = "<profile>\n" +
            "    gallery(0-59): sprite\\sys\\henry_f.bmp w: 79  h: 79  row: 10  col: 7\n" +
            "    gallery(60-100): sprite\\sys\\henry_s.bmp w: 79  h: 79  row: 10  col: 7\n" +
            "</profile>\n" +
            "\n" +
            "<frame> 1 punch\n" +
            "   pic: 13  duration: 1  next: 64  dvx: 0  dvy: 0  dvz: 0  \n" +
            "   sound: \\s24.wav\n" +
            "   <body>\n" +
            "      kind: 0  x: 28  y: 34  w: 42  h: 19\n" +
            "   </body>\n" +
            "   <bow> \n" +
            "      kind: 2  x: 21  y: 57  w: 37  h: 24  vrest: 1  \n" +
            "   </bow>\n" +
            "</frame>";

    @Test
    public void testParser(){
        FrameStateMachineComponent fsmc = parser.parse(SCRIPT, new MyParserListener());

    }

    private class MyParserListener implements FrameStateMachineScriptParser.OnParsingFrameListener {
        @Override
        public BiConsumer<AppStateWorld, Sprite> onParsing(FrameSegment segment) {
            return null;
        }
    }

    private class MyParserConsumer implements BiConsumer<AppStateWorld, Sprite> {
        @Override
        public void accept(AppStateWorld world, Sprite sprite) {

        }
    }

}
