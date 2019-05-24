package com.pokewords.framework.sprites.parsing;


import com.pokewords.framework.commons.utils.FileUtility;

import java.io.IOException;

/**
 * @author nyngwang
 */
public interface ScriptSample {
    interface LinScript {
        String SCRIPT_TEXT =
                "<parameters>\n" +
                "    speed: 8\n" +
                "</parameters>\n" +
                "\n" +
                "<galleries>\n" +
                "    <sheet>\n" +
                "        startPic: 0 endPic: 6 path: sheets/character.png\n" +
                "    </sheet>\n" +
                "</galleries>\n" +
                "\n" +
                "<frame> 0 walkLeft\n" +
                "    duration: 80 next: 1 pic: 0 layer: 2\n" +
                "\t<transitions>\n" +
                "        classType: com.demo.Types\n" +
                "\t\twalkLeft: 0\n" +
                "\t\twalkRight: 3\n" +
                "\t\twalkUp: 6\n" +
                "\t\twalkDown: 9\n" +
                "\t</transitions>\n" +
                "    <body>\n" +
                "        x: 16 y: 10 w: 49 h: 67\n" +
                "    </body>\n" +
                "    <effect>\n" +
                "        moveX: -$speed\n" +
                "    </effect>\n" +
                "</frame>\n" +
                "\n" +
                "<frame> 1 walkLeft\n" +
                "    duration: 80 next: 2 pic: 1 layer: 2\n" +
                "\t<transitions>\n" +
                "        classType: com.demo.Types\n" +
                "\t\twalkLeft: 0\n" +
                "\t\twalkRight: 3\n" +
                "\t\twalkUp: 6\n" +
                "\t\twalkDown: 9\n" +
                "\t</transitions>\n" +
                "    <body>\n" +
                "        x: 16 y: 10 w: 49 h: 67\n" +
                "    </body>\n" +
                "    <effect>\n" +
                "        moveX: -8\n" +
                "    </effect>\n" +
                "</frame>\n" +
                "\n" +
                "<frame> 2 walkLeft\n" +
                "    duration: 80 next: 0 pic: 4 layer: 2\n" +
                "\t<transitions>\n" +
                "        classType: com.demo.Types\n" +
                "\t\twalkLeft: 0\n" +
                "\t\twalkRight: 3\n" +
                "\t\twalkUp: 6\n" +
                "\t\twalkDown: 9\n" +
                "\t</transitions>\n" +
                "    <body>\n" +
                "        x: 16 y: 10 w: 49 h: 67\n" +
                "    </body>\n" +
                "    <effect>\n" +
                "        moveX: -8\n" +
                "    </effect>\n" +
                "</frame>\n" +
                "\n" +
                "\n" +
                "<frame> 3 walkRight\n" +
                "    duration: 80 next: 4 pic: 2 layer: 2\n" +
                "\t<transitions>\n" +
                "        classType: com.demo.Types\n" +
                "\t\twalkLeft: 0\n" +
                "\t\twalkRight: 3\n" +
                "\t\twalkUp: 12\n" +
                "\t\twalkDown: 15\n" +
                "\t</transitions>\n" +
                "    <body>\n" +
                "        x: 9 y: 11 w: 44 h: 64\n" +
                "    </body>\n" +
                "    <effect>\n" +
                "        moveX: 8\n" +
                "    </effect>\n" +
                "</frame>\n" +
                "\n" +
                "<frame> 4 walkRight\n" +
                "    duration: 80 next: 5 pic: 5 layer: 2\n" +
                "\t<transitions>\n" +
                "        classType: com.demo.Types\n" +
                "\t\twalkLeft: 0\n" +
                "\t\twalkRight: 3\n" +
                "\t\twalkUp: 12\n" +
                "\t\twalkDown: 15\n" +
                "\t</transitions>\n" +
                "    <body>\n" +
                "        x: 9 y: 11 w: 44 h: 64\n" +
                "    </body>\n" +
                "    <effect>\n" +
                "        moveX: 8\n" +
                "    </effect>\n" +
                "</frame>\n" +
                "\n" +
                "<frame> 5 walkRight\n" +
                "    duration: 80 next: 3 pic: 6 layer: 2\n" +
                "\t<transitions>\n" +
                "        classType: com.demo.Types\n" +
                "\t\twalkLeft: 0\n" +
                "\t\twalkRight: 3\n" +
                "\t\twalkUp: 12\n" +
                "\t\twalkDown: 15\n" +
                "\t</transitions>\n" +
                "    <body>\n" +
                "        x: 9 y: 11 w: 44 h: 64\n" +
                "    </body>\n" +
                "    <effect>\n" +
                "        moveX: 8\n" +
                "    </effect>\n" +
                "</frame>\n" +
                "\n" +
                "\n" +
                "<frame> 6 walkLeftUp\n" +
                "    duration: 80 next: 7 pic: 0 layer: 2\n" +
                "\t<transitions>\n" +
                "        classType: com.demo.Types\n" +
                "\t\twalkLeft: 0\n" +
                "\t\twalkRight: 3\n" +
                "\t\twalkUp: 6\n" +
                "\t\twalkDown: 9\n" +
                "\t</transitions>\n" +
                "    <body>\n" +
                "        x: 16 y: 10 w: 49 h: 67\n" +
                "    </body>\n" +
                "    <effect>\n" +
                "        moveY: -7\n" +
                "    </effect>\n" +
                "</frame>\n" +
                "\n" +
                "<frame> 7 walkLeftUp\n" +
                "    duration: 80 next: 8 pic: 1 layer: 2\n" +
                "\t<transitions>\n" +
                "        classType: com.demo.Types\n" +
                "\t\twalkLeft: 0\n" +
                "\t\twalkRight: 3\n" +
                "\t\twalkUp: 6\n" +
                "\t\twalkDown: 9\n" +
                "\t</transitions>\n" +
                "    <body>\n" +
                "        x: 16 y: 10 w: 49 h: 67\n" +
                "    </body>\n" +
                "    <effect>\n" +
                "\t\tmoveY: -7\n" +
                "    </effect>\n" +
                "</frame>\n" +
                "\n" +
                "<frame> 8 walkLeftUp\n" +
                "    duration: 80 next: 6 pic: 4 layer: 2\n" +
                "\t<transitions>\n" +
                "        classType: com.demo.Types\n" +
                "\t\twalkLeft: 0\n" +
                "\t\twalkRight: 3\n" +
                "\t\twalkUp: 6\n" +
                "\t\twalkDown: 9\n" +
                "\t</transitions>\n" +
                "    <body>\n" +
                "        x: 16 y: 10 w: 49 h: 67\n" +
                "    </body>\n" +
                "    <effect>\n" +
                "        moveY: -7\n" +
                "    </effect>\n" +
                "</frame>\n" +
                "\n" +
                "\n" +
                "<frame> 9 walkLeftDown\n" +
                "    duration: 80 next: 10 pic: 0 layer: 2\n" +
                "\t<transitions>\n" +
                "        classType: com.demo.Types\n" +
                "\t\twalkLeft: 0\n" +
                "\t\twalkRight: 3\n" +
                "\t\twalkUp: 6\n" +
                "\t\twalkDown: 9\n" +
                "\t</transitions>\n" +
                "    <body>\n" +
                "        x: 16 y: 10 w: 49 h: 67\n" +
                "    </body>\n" +
                "    <effect>\n" +
                "        moveY: 7\n" +
                "    </effect>\n" +
                "</frame>\n" +
                "\n" +
                "<frame> 10 walkLeftDown\n" +
                "    duration: 80 next: 11 pic: 1 layer: 2\n" +
                "\t<transitions>\n" +
                "        classType: com.demo.Types\n" +
                "\t\twalkLeft: 0\n" +
                "\t\twalkRight: 3\n" +
                "\t\twalkUp: 6\n" +
                "\t\twalkDown: 9\n" +
                "\t</transitions>\n" +
                "    <body>\n" +
                "        x: 16 y: 10 w: 49 h: 67\n" +
                "    </body>\n" +
                "    <effect>\n" +
                "        moveY: 7\n" +
                "    </effect>\n" +
                "</frame>\n" +
                "\n" +
                "<frame> 11 walkLeftDown\n" +
                "    duration: 80 next: 9 pic: 4 layer: 2\n" +
                "\t<transitions>\n" +
                "        classType: com.demo.Types\n" +
                "\t\twalkLeft: 0\n" +
                "\t\twalkRight: 3\n" +
                "\t\twalkUp: 6\n" +
                "\t\twalkDown: 9\n" +
                "\t</transitions>\n" +
                "    <body>\n" +
                "        x: 16 y: 10 w: 49 h: 67\n" +
                "    </body>\n" +
                "    <effect>\n" +
                "        moveY: 7\n" +
                "    </effect>\n" +
                "</frame>\n" +
                "\n" +
                "<frame> 12 walkRightUp\n" +
                "    duration: 80 next: 13 pic: 2 layer: 2\n" +
                "\t<transitions>\n" +
                "        classType: com.demo.Types\n" +
                "\t\twalkLeft: 0\n" +
                "\t\twalkRight: 3\n" +
                "\t\twalkUp: 12\n" +
                "\t\twalkDown: 15\n" +
                "\t</transitions>\n" +
                "    <body>\n" +
                "        x: 9 y: 11 w: 44 h: 64\n" +
                "    </body>\n" +
                "    <effect>\n" +
                "        moveY: -7\n" +
                "    </effect>\n" +
                "</frame>\n" +
                "\n" +
                "<frame> 13 walkRightUp\n" +
                "    duration: 80 next: 14 pic: 5 layer: 2\n" +
                "\t<transitions>\n" +
                "        classType: com.demo.Types\n" +
                "\t\twalkLeft: 0\n" +
                "\t\twalkRight: 3\n" +
                "\t\twalkUp: 12\n" +
                "\t\twalkDown: 15\n" +
                "\t</transitions>\n" +
                "    <body>\n" +
                "        x: 9 y: 11 w: 44 h: 64\n" +
                "    </body>\n" +
                "    <effect>\n" +
                "        moveY: -7\n" +
                "    </effect>\n" +
                "</frame>\n" +
                "\n" +
                "<frame> 14 walkRightUp\n" +
                "    duration: 80 next: 12 pic: 6 layer: 2\n" +
                "\t<transitions>\n" +
                "        classType: com.demo.Types\n" +
                "\t\twalkLeft: 0\n" +
                "\t\twalkRight: 3\n" +
                "\t\twalkUp: 12\n" +
                "\t\twalkDown: 15\n" +
                "\t</transitions>\n" +
                "    <body>\n" +
                "        x: 9 y: 11 w: 44 h: 64\n" +
                "    </body>\n" +
                "    <effect>\n" +
                "        moveY: -7\n" +
                "    </effect>\n" +
                "</frame>\n" +
                "\n" +
                "<frame> 15 walkRightDown\n" +
                "    duration: 80 next: 16 pic: 2 layer: 2\n" +
                "\t<transitions>\n" +
                "        classType: com.demo.Types\n" +
                "\t\twalkLeft: 0\n" +
                "\t\twalkRight: 3\n" +
                "\t\twalkUp: 12\n" +
                "\t\twalkDown: 15\n" +
                "\t</transitions>\n" +
                "    <body>\n" +
                "        x: 9 y: 11 w: 44 h: 64\n" +
                "    </body>\n" +
                "    <effect>\n" +
                "        moveY: 7\n" +
                "    </effect>\n" +
                "</frame>\n" +
                "\n" +
                "<frame> 16 walkRightUp\n" +
                "    duration: 80 next: 17 pic: 5 layer: 2\n" +
                "\t<transitions>\n" +
                "        classType: com.demo.Types\n" +
                "\t\twalkLeft: 0\n" +
                "\t\twalkRight: 3\n" +
                "\t\twalkUp: 12\n" +
                "\t\twalkDown: 15\n" +
                "\t</transitions>\n" +
                "    <body>\n" +
                "        x: 9 y: 11 w: 44 h: 64\n" +
                "    </body>\n" +
                "    <effect>\n" +
                "        moveY: 7\n" +
                "    </effect>\n" +
                "</frame>\n" +
                "\n" +
                "<frame> 17 walkRightUp\n" +
                "    duration: 80 next: 15 pic: 6 layer: 2\n" +
                "\t<transitions>\n" +
                "        classType: com.demo.Types\n" +
                "\t\twalkLeft: 0\n" +
                "\t\twalkRight: 3\n" +
                "\t\twalkUp: 12\n" +
                "\t\twalkDown: 15\n" +
                "\t</transitions>\n" +
                "    <body>\n" +
                "        x: 9 y: 11 w: 44 h: 64\n" +
                "    </body>\n" +
                "    <effect>\n" +
                "        moveY: 7\n" +
                "    </effect>\n" +
                "</frame>";
    }
}
