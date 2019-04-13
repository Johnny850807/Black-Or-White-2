package com.pokewords.framework.sprites.parsing;

public class FrameSegment implements Node {
    public final static String PIC = "pic";
    public final static String DURATION = "duration";
    public final static String NEXT = "next";

    private Script script;

    public String getFrameName(){
        return null; //TODO
    }

    public int getFrameNumber(){
        return 0; //TODO
    }

    public Element getElement(String name){
        return null; //TODO
    }

    public String getString(String name){
        return null; //TODO
    }

    public int getInt(String name){
        return 0; //TODO
    }

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    @Override
    public void interpret(Script script) {

    }
}
