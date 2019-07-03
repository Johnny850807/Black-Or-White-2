/*MIT License
Copyright (c) 2018 WaterBall

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
==============================================================================*/

package com.pokewords.framework.stages;

import com.pokewords.framework.commons.utils.Resources;
import com.pokewords.framework.engine.listeners.GameLoopingListener;
import com.pokewords.framework.sprites.parsing.*;

import java.util.ArrayList;
import java.util.List;

public class StageSystem implements GameLoopingListener {

    private List<Stage> stages = new ArrayList<>();

    public StageSystem(String path){
        parserStageInfomation(path);
    }

    public void parserStageInfomation(String path){
        Script scriptProfileScript = new LinScript();
        scriptProfileScript.parse(Context.fromFile(Resources.get(path))); //"script/StageProfileScript.bow"

        Segment StagesSegment = scriptProfileScript.getFirstSegment("stages");
        List<Element> stageElement = StagesSegment.getElements("stage");

        for (Element elementStage : stageElement) {
            int number = elementStage.getInt("number");
            String name = elementStage.getString("name");
            String soundPath = elementStage.getString("bgm");
            String stageFilePath = elementStage.getString("path");
            stages.add(new Stage(number, name, soundPath, stageFilePath));
        }


    }

    public List<Stage> getStages() {
        return stages;
    }

    @Override
    public void onUpdate(double timePerFrame) {

    }
}
