package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.utils.FileUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Context {
    private List<String> tokens = new ArrayList<>();
    private String filePath;
    private String currentToken;

    public Context(String filePath) {
        this.filePath = filePath;
        parseToken();
    }

    private void parseToken() {
        try {
            String fileContent = FileUtility.read(filePath);
            Collections.addAll(tokens, fileContent.split("\\s+"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public boolean fetchNextToken() {
        if (tokens.size() > 0) {
            currentToken = tokens.remove(0);
            return true;
        }
        return false;
    }

    public String getCurrentToken() {
        return currentToken;
    }

    public static void main(String[] args) {
        Context context = new Context("path/to/script_text");
        while (context.fetchNextToken()) {
            String token = context.getCurrentToken();
        }
    }
}
