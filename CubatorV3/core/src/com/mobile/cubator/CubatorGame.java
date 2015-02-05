package com.mobile.cubator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mobile.helpers.AssetLoader;
import com.mobile.screens.GameScreen;

public class CubatorGame extends Game{
    @Override
    public void create() {
        Gdx.app.log("ZBGame", "created");
        AssetLoader.load();
        setScreen(new GameScreen());
    }

    @Override
    public void dispose(){
        super.dispose();
        AssetLoader.dispose();
    }
}
