package com.pokewords.framework.sprites;

import com.pokewords.framework.commons.utils.FileUtility;
import com.pokewords.framework.sprites.parsing.Context;

import java.io.IOException;

public class ScriptTextGenerator {

    public static String fromTemplate(String template) {
        return new ScriptTextGenerator().generate(template);
    }

    public static String fromPath(String path) {
        String template;
        try {
            template = FileUtility.read(path);
            return fromTemplate(template);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generate(String template) {
        Context context = Context.fromText(template);

        return template;
    }

    public static void main(String[] args) {
        System.out.println(ScriptTextGenerator.fromPath("bow-engine/src/main/resources/assets/scripts/templateTest.bow"));
    }
}
