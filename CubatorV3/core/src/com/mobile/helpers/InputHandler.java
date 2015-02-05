package com.mobile.helpers;

import com.badlogic.gdx.InputProcessor;
import com.mobile.cubator.CubatorGame;
import com.mobile.gameobjects.Player;
import com.mobile.gameworld.GameWorld;
import com.mobile.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by justin on 2/3/2015.
 */
public class InputHandler implements InputProcessor{
    private Player player;
    private GameWorld myWorld;
    private List<SimpleButton> menuButtons;
    private  SimpleButton playButton;

    private float scaleFactorX,scaleFactorY;


    public InputHandler(GameWorld world, float SX, float SY)
    {

        this.myWorld = world;
        this.player = world.getPlayer();
        int midPointY = myWorld.getMidPointY();

        this.scaleFactorX = SX;
        this.scaleFactorY = SY;

        menuButtons = new ArrayList<SimpleButton>();
        playButton = new SimpleButton(
                CubatorGame.worldWidth / 2 - (AssetLoader.playButtonUp.getRegionWidth() / 2),
                midPointY + 50, 29, 16, AssetLoader.playButtonUp,
                AssetLoader.playButtonDown);
        menuButtons.add(playButton);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        if (myWorld.isMenu()) {
            playButton.isTouchDown(screenX, screenY);
        }else if (myWorld.isReady()) {
            myWorld.start();
        }
        player.onClick(screenX, screenY);
        if (myWorld.isGameOver()){
            myWorld.restart();
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (myWorld.isMenu()) {
            if (playButton.isTouchUp(screenX, screenY)) {
                myWorld.ready();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        player.onTouch(screenX, screenY);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (screenY / scaleFactorY);
    }

    public List<SimpleButton> getMenuButtons() {
        return menuButtons;
    }
}
