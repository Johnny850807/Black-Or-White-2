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

import com.pokewords.framework.sprites.parsing.Element;

/**
 * @author shawn
 */
public class StageElement {
    private int number;
    private String name;
    private String stageScriptFilePath;

    public StageElement(Element element) {
        this.number = element.getInt("number");
        this.name = element.getString("name");
        this.stageScriptFilePath = element.getString("stageScriptFilePath");
    }


    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getStageScriptFilePath() {
        return stageScriptFilePath;
    }
}
