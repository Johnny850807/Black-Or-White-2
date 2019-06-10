package com.pokewords.constants;

import com.pokewords.framework.commons.utils.Resources;
import com.pokewords.framework.sprites.parsing.Context;
import com.pokewords.framework.sprites.parsing.LinScript;
import com.pokewords.framework.sprites.parsing.Script;

import java.awt.*;

public interface Theme {
    Color mainColor = Color.decode("#191F26");

    static void main(String[] args) {
        Script linScript = new LinScript();
        linScript.parse(Context.fromFile(Resources.get("assets/scripts/evil.bow")));
        System.out.println(linScript.toString(4));
    }
}
