package com.pokewords.framework.engine.asm;

import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.engine.weaver.Set0FrameAsCurrentNodeWeaverNode;
import com.pokewords.framework.sprites.parsing.*;

public class BreakerIconLoadingState extends AppState {

    private enum Types {
        BreakerIconLoadingState
    }

    @Override
    protected void onAppStateCreating(AppStateWorld appStateWorld) {
        getGameWindowsConfigurator().size(800, 600)
                                    .atCenter();

        declareBreakerIconLoadingAnimation();

    }

    private void declareBreakerIconLoadingAnimation() {
        getAppStateWorld().spawn(
                getSpriteInitializer().declare(Types.BreakerIconLoadingState)
                            .position(0, 0)
                            .size(getGameWindowDefinition().size)
                            .with(createScript())
                            .weaver(new Set0FrameAsCurrentNodeWeaverNode())
                            .commit()
                            .create()
        );
    }

    @SuppressWarnings("Duplicates")
    private Script createScript() {
        Script script = new LinScript();
        Segment galleriesSegment = new LinScriptSegment("galleries", 0);
        Element sequenceElement = new LinScriptElement("sequence");
        sequenceElement.put("startPic", 0);
        sequenceElement.put("endPic", 249);
        sequenceElement.put("path", "assets/sequences/BeakerLoadingIcon");
        script.addSegment(galleriesSegment);
        galleriesSegment.addElement(sequenceElement);

        for (int i = 0; i <= 249; i++) {
            LinScriptSegment frameSegment = new LinScriptSegment("frame", i);
            frameSegment.put("pic", i);
            frameSegment.put("duration", 50);
            frameSegment.put("next", (i+1)%250);
            frameSegment.put("layer", 0);
            script.addSegment(frameSegment);
        }
        return script;
    }

    @Override
    protected void onAppStateEntering() { }

    @Override
    protected void onAppStateExiting() { }

    @Override
    protected void onAppStateDestroying() { }

    @Override
    protected void onAppStateUpdating(int timePerFrame) { }
}
