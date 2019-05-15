package com.pokewords.framework.engine.asm;

import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.components.StringComponent;

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
	public void onAppStateCreating(AppStateWorld appStateWorld) {
		getSpriteInitializer().declare(LOADING_TEXT)
							.with(new StringComponent("Loading ..."));
	}

	@Override
	public void onAppStateEntering() {
		originalColor = currentColor = getGameWindowDefinition().gamePanelBackground;
	}


	private int addOrMinute = 1;

	@Override
	public void onAppStateUpdating(int timePerFrame) {
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
