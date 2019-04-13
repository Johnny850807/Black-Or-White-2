package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.components.FrameFactory;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;

public class FrameStateMachineScriptParserImp implements FrameStateMachineScriptParser {
    private FrameFactory frameFactory;

    public FrameStateMachineScriptParserImp(IocFactory iocFactory) {
        this.frameFactory = iocFactory.frameFactory();
    }

    @Override
    public FrameStateMachineComponent parse(Script script, OnParsingFrameListener listener) {



        // When parsing new Element if appropriate.

        //

        for (FrameSegment fs : fss) {
            listener.onParsing(eachFrameSegment);
        }










        return null;
    }


    // 用FrameFactory建立FrameNode
    // 改回Script
    // 改傳FrameSegment給Facotry
    // FrameSegment
    // 清楚的ParsingException
}
