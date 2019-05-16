package com.pokewords.framework.engine.asm;

import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.CloneableComponent;
import com.pokewords.framework.sprites.components.ImageComponent;
import com.pokewords.framework.sprites.components.StringComponent;
import com.pokewords.framework.sprites.components.frames.ImageFrame;
import com.pokewords.framework.sprites.components.frames.StringFrame;
import com.pokewords.framework.sprites.components.marks.Shareable;

import java.awt.*;

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
                                .gamePanelBackground(Color.decode("#1B4032"))
                                .atCenter();
    }

    private void configCenterLoadingText() {
        getSpriteInitializer().declare(CENTER_LOADING_TEXT)
                .position(getGameWindowDefinition().center())
                .with(new StringComponent(
                        new StringFrame(0, 2, "Loading",
                                new Font("Verdana", Font.PLAIN, 25), true)))
                .with(new LoadingTextComponent())
                .commit();
    }

    private void configTitleText() {
        getSpriteInitializer().declare(TITLE_TEXT)
                .position(300, 150)
                .with(new StringComponent(new StringFrame(0, 2, "Bow Engine Demo",
                        Color.blue, new Font("AR CENA", Font.BOLD, 40), true)))
                .commit();
    }

    private void configJoanna() {
        getSpriteInitializer().declare(JOANNA)
                .position(173, 319)
                .with(new ImageComponent(new ImageFrame(0, 2, 254, 263,
                        "assets/images/joanna.png")))
                .commit();
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


    private class LoadingTextComponent extends CloneableComponent implements Shareable {
        private Sprite sprite;
        private int time = 0;
        private int frequency = 15;
        private int dot = 0;
        private int maxDot = 5;

        @Override
        public void onUpdate(int timePerFrame) {
            StringComponent stringComponent = sprite.getComponent(StringComponent.class);
            StringFrame stringFrame = stringComponent.getFrame();

            if (time++ == frequency) {
                time = 0;
                if (dot++ == maxDot) {
                    dot = 0;
                    stringFrame.setText("Loading");
                } else
                    stringFrame.setText(stringFrame.getText() + ".");
            }
        }
    }

    @Override
    protected void onAppStateExiting() {

    }

    @Override
    protected void onAppStateDestroying() {

    }
}
