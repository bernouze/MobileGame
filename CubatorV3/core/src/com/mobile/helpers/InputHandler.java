package com.mobile.helpers;

import com.badlogic.gdx.InputProcessor;
import com.mobile.gameobjects.Player;

/**
 * Created by justin on 2/3/2015.
 */
public class InputHandler implements InputProcessor{
    private Player player;

    public InputHandler(Player player)
    {
        this.player = player;
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
        player.onClick(screenX, screenY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        player.resetMove();
        return true;
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
}
