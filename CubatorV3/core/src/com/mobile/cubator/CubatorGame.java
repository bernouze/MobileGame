package com.mobile.cubator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mobile.helpers.AssetLoader;
import com.mobile.screens.GameScreen;
import com.mobile.screens.SplashScreen;

public class CubatorGame extends Game{
    public static final float worldWidth = 1280;

    @Override
    public void create() {
        Gdx.app.log("Cubator", "created");
        AssetLoader.load();
        setScreen(new SplashScreen(this));
    }

    @Override
    public void dispose(){
        super.dispose();
        AssetLoader.dispose();
    }
}
