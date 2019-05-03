package com.pokewords.framework.engine.asm;

import com.pokewords.framework.sprites.components.StringsComponent;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

import java.awt.*;

import static com.pokewords.framework.engine.asm.LoadingState.ComponentName.STRINGS;
import static com.pokewords.framework.engine.asm.LoadingState.SpriteName.LOADING_TEXT;

public class LoadingState extends AppState {
	private Color originalColor;
	private Color currentColor;

	enum SpriteName {
		LOADING_TEXT
	}

	enum ComponentName {
		STRINGS
	}

	@Override
	public void onAppStateStart(AppStateWorld world) {
		super.onAppStateStart(world);

		getSpriteInitializer().declare(LOADING_TEXT)
							.with(STRINGS, createLoadingStringsComponent());
	}

	private StringsComponent createLoadingStringsComponent() {
		StringsComponent stringsComponent = new StringsComponent();
		stringsComponent.addString(getGameWindowDefinition().center(), "Loading ...");
		return stringsComponent;
	}

	@Override
	public void onAppStateEnter() {
		originalColor = currentColor = getGameWindowDefinition().gamePanelBackground;
	}

	@Override
	public void onAppStateExit() {

	}

	@Override
	public void onAppStateDestroy() {

	}

	private int addOrMinute = 1;

	@Override
	public void onUpdate(double timePerFrame) {
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
}
