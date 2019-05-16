package com.pokewords.framework.engine.asm;

import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.engine.utils.Resources;
import com.pokewords.framework.engine.weaver.Set0FrameAsCurrentNodeWeaverNode;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.CloneableComponent;
import com.pokewords.framework.sprites.components.ImageComponent;
import com.pokewords.framework.sprites.components.StringComponent;
import com.pokewords.framework.sprites.components.frames.ImageFrame;
import com.pokewords.framework.sprites.components.frames.StringFrame;
import com.pokewords.framework.sprites.components.marks.Shareable;
import com.pokewords.framework.sprites.parsing.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.pokewords.framework.engine.asm.LoadingState.SpriteName.*;

/**
 * The built-in loading state
 * @author johnny850807 (waterball)
 */
public class LoadingState extends AppState {
    private Color originalColor;
    private Color currentColor;

    enum SpriteName {
        CENTER_LOADING_TEXT, TITLE_TEXT, JOANNA
    }


    @Override
    public void onAppStateCreating(AppStateWorld appStateWorld) {
        configGameWindow();
        configCenterLoadingText();
        configJoanna();
        configTitleText();

        getAppStateWorld().spawn(createSprite(CENTER_LOADING_TEXT));
        getAppStateWorld().spawn(createSprite(TITLE_TEXT));
        getAppStateWorld().spawn(createSprite(JOANNA));
    }

    private void configGameWindow() {
        getGameWindowsConfigurator().size(600, 600)
                                .gamePanelBackground(Color.decode("#20053f"))
                                .atCenter();
    }

    private void configCenterLoadingText() {
        getSpriteInitializer().declare(CENTER_LOADING_TEXT)
                .position(getGameWindowDefinition().center())
                .with(createScript())
                .weaver(new Set0FrameAsCurrentNodeWeaverNode())
                .commit();
    }

    private Script createScript() {
        Script script = new LinScript();
        Segment galleriesSegment = new LinScriptSegment("galleries", 0);
        Element sequenceElement = new LinScriptElement("sequence");
        sequenceElement.put("startPic", 0);
        sequenceElement.put("endPic", 11);
        sequenceElement.put("path", "assets/sequences/loadingProgressBar");
        script.addSegment(galleriesSegment);
        galleriesSegment.addElement(sequenceElement);

        for (int i = 0; i <= 11; i++) {
            LinScriptSegment frameSegment = new LinScriptSegment("frame", i);
            frameSegment.put("pic", i);
            frameSegment.put("duration", 1);
            frameSegment.put("next", (i+1)%12);
            frameSegment.put("layer", 2);
            script.addSegment(frameSegment);
        }
        return script;
    }

    private void configTitleText() {
        getSpriteInitializer().declare(TITLE_TEXT)
                .position(300, 150)
                .with(new StringComponent(new StringFrame(0, 2, "Bow Engine Demo",
                        Color.blue, new Font("AR CENA", Font.BOLD, 40), true)))
                .commit();
    }

    private void configJoanna() {
        try {
            BufferedImage image = ImageIO.read(Resources.get("assets/images/joanna2.jpg"));
            int width = image.getWidth() * getWindowSize().y / image.getHeight() / 2;
            int height = getWindowSize().y / 2;
            getSpriteInitializer().declare(JOANNA)
                    .position((getWindowSize().x - width) / 2, getWindowSize().y - height)
                    .with(new ImageComponent(new ImageFrame(0, 2, width, height, image)))
                    .commit();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onAppStateEntering() {
        originalColor = currentColor = getGameWindowDefinition().gamePanelBackground;
    }


    private int addOrMinute = 1;

    @Override
    public void onAppStateUpdating(int timePerFrame) {
        updateBackgroundColor();
    }

    private void updateBackgroundColor() {
        int red = currentColor.getRed() + addOrMinute;
        int green = currentColor.getGreen() + addOrMinute;
        int blue = currentColor.getBlue() + addOrMinute;

        if (red == 255 || green == 255 || blue == 255)
            addOrMinute = -1;
        if (red == originalColor.getRed() || green == originalColor.getGreen() || blue == originalColor.getBlue())
            addOrMinute = 1;

        currentColor = new Color(red, green, blue);

        getGameWindowsConfigurator().gamePanelBackground(currentColor);
    }


    @Override
    protected void onAppStateExiting() {

    }

    @Override
    protected void onAppStateDestroying() {

    }
}
