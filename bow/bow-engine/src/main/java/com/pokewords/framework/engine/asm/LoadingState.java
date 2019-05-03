package com.pokewords.framework.engine.asm;

import java.awt.*;

public class LoadingState extends AppState {
	private Color originalColor;
	private Color currentColor;

	enum SpriteName {
		LOADING_TEXT
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
