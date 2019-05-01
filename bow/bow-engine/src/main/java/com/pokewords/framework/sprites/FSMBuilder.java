package com.pokewords.framework.sprites;

public class FSMBuilder {
    String string =
            "<gallery> 0 henry-gallery1\n" +
            "    startPic: 0 endPic: 59 path: sprite\\sys\\henry_f.bmp\n" +
            "    w: 50  h: 50  row: 10  col: 10\n" +
            "</gallery>\n" +
            "\n" +
            "<gallery> 1 henry-gallery2\n" +
            "    startPic: 60 endPic: 100 path: sprite\\sys\\henry_f.bmp\n" +
            "    w: 50  h: 50  row: 10  col: 10\n" +
            "</gallery>\n" +
            "\n" +
            "<frame> 0 punch\n" +
            "   pic: 0  duration: 1  next: 1  dvx: 0  dvy: 0 centerX: 50 centerY: 50 layer: 0\n" +
            "   <bow>\n" +
            "       state: 0\n" +
            "   </bow>\n" +
            "   <body>\n" +
            "      x: 28  y: 34  w: 42  h: 19\n" +
            "   </body>\n" +
            "</frame>\n" +
            "\n" +
            "<frame> 1 punch\n" +
            "   pic: 1  duration: 1  next: 2  dvx: 0  dvy: 0 centerX: 50 centerY: 50 layer: 0\n" +
            "   <bow>\n" +
            "       state: 0\n" +
            "   </bow>\n" +
            "   <body>\n" +
            "      x: 28  y: 34  w: 42  h: 19\n" +
            "   </body>\n" +
            "</frame>\n" +
            "\n" +
            "<frame> 2 punch\n" +
            "   pic: 2  duration: 1  next: 3  dvx: 0  dvy: 0 centerX: 50 centerY: 50 layer: 0\n" +
            "   <bow>\n" +
            "       state: 0\n" +
            "   </bow>\n" +
            "   <body>\n" +
            "      x: 28  y: 34  w: 42  h: 19\n" +
            "   </body>\n" +
            "</frame>\n" +
            "\n" +
            "<frame> 3 punch\n" +
            "   pic: 3  duration: 1  next: 0  dvx: -1  dvy: 0 centerX: 50 centerY: 50 layer: 0\n" +
            "   sound: sound\\s24.wav\n" +
            "   zxc: hello\n" +
            "   <bow>\n" +
            "       state: 0 injury: 10 \n" +
            "   </bow>\n" +
            "   <body>\n" +
            "      x: 28  y: 34  w: 42  h: 19\n" +
            "   </body>\n" +
            "</frame>";
}
