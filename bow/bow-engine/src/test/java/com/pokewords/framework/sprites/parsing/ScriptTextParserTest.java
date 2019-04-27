package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.AbstractTest;
import com.pokewords.framework.sprites.components.FrameFactory;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScriptTextParserTest extends AbstractTest {
    ScriptTextParser parser = release.scriptTextParser();
    FrameFactory frameFactory = mock.frameFactory();

    final String SCRIPT =
            "<gallery>\n" +
             "    startPic: 0 endPic: 59 path: sprite\\sys\\henry_f.bmp\n" +
             "    w: 50  h: 50  row: 10  col: 10\n" +
             "</gallery>\n" +
             "\n" +
             "<gallery>\n" +
             "    startPic: 60 endPic: 100 path: sprite\\sys\\henry_f.bmp\n" +
             "    w: 50  h: 50  row: 10  col: 10\n" +
             "</gallery>\n" +
             "\n" +
             "<frame> 0 punch\n" +
             "   pic: 0  duration: 1  next: 1  dvx: 0  dvy: 0 \n" +
             "   <bow>\n" +
             "       state: 0\n" +
             "   </bow>\n" +
             "   <body>\n" +
             "      x: 28  y: 34  w: 42  h: 19\n" +
             "   </body>\n" +
             "</frame>\n" +
             "\n" +
             "<frame> 1 punch\n" +
             "   pic: 1  duration: 1  next: 2  dvx: 0  dvy: 0 \n" +
             "   <bow>\n" +
             "       state: 0\n" +
             "   </bow>\n" +
             "   <body>\n" +
             "      x: 28  y: 34  w: 42  h: 19\n" +
             "   </body>\n" +
             "</frame>\n" +
             "\n" +
             "<frame> 2 punch\n" +
             "   pic: 2  duration: 1  next: 3  dvx: 0  dvy: 0 \n" +
             "   <bow>\n" +
             "       state: 0\n" +
             "   </bow>\n" +
             "   <body>\n" +
             "      x: 28  y: 34  w: 42  h: 19\n" +
             "   </body>\n" +
             "</frame>\n" +
             "\n" +
             "<frame> 3 punch\n" +
             "   pic: 3  duration: 1  next: 0  dvx: -1  dvy: 0 \n" +
             "   sound: sound\\s24.wav\n" +
             "   <bow>\n" +
             "       state: 0 injury: 10 \n" +
             "   </bow>\n" +
             "   <body>\n" +
             "      x: 28  y: 34  w: 42  h: 19\n" +
             "   </body>\n" +
             "</frame>";

    /**
     * Because the parser triggers the listener from the first frame to the last frame,
     * this integer records the parsing frame number.
     */
    private int frameNumber = 0;
    private FrameStateMachineComponent expectedFsmComponent = new FrameStateMachineComponent();

    @Before
    public void initExpectedFsmComponent(){
        expectedFsmComponent.addState(frameFactory.createFrame());
    }

    @Test
    public void testParser(){
        FrameStateMachineComponent fsmComponent = parser.parse(SCRIPT, frame -> {
            /* Client custom Parsing */
            assertEquals(frameNumber++, frame.getFrameNumber());
            assertEquals((frameNumber + 1) % 4, frame.getInt("next"));
            assertEquals(frameNumber, frame.getInt("pic"));
            assertEquals(1, frame.getInt("duration"));
            assertEquals(0, frame.getInt("dvy"));

            Element bodyElm = frame.getElement("body");
            assertEquals(28, bodyElm.getInt("X"));
            assertEquals(34, bodyElm.getInt("y"));
            assertEquals(42, bodyElm.getInt("w"));
            assertEquals(19, bodyElm.getInt("h"));

            Element bowElm = frame.getElement("bow");
            assertEquals(0, bowElm.getInt("state"));

            if (frameNumber != 4)
            {
                assertEquals(0, frame.getInt("dvx"));
            }
            else
            {
                assertEquals(10, bowElm.getInt("injury"));
                assertEquals(-1, frame.getInt("dvx"));
                assertEquals("sound\\s24.wav", frame.getString("sound"));
            }

            return (world, sprite) -> {
                /* Consumer */

            };
        });

    }

}
