package com.pokewords.framework.sprites.parsing;

public interface Node {

    Node parse();




    static void main(String[] args) {

        Script script = new Script.parse("input-string");

    }
}
