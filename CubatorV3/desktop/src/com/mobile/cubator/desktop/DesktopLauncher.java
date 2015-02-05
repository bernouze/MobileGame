package com.mobile.cubator.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mobile.cubator.CubatorGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Cubator";
        config.width = 1280;
        config.height = 720;
		new LwjglApplication(new CubatorGame(), config);
	}
}
