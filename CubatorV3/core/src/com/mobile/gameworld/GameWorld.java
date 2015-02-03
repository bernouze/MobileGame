package com.mobile.gameworld;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.math.Rectangle;
import com.mobile.gameobjects.Player;

/**
 * Created by justin on 2/3/2015.
 */
public class GameWorld {
    private Player player;

    public GameWorld(int midPointY)
    {
        player = new Player(33,midPointY - 5, 17,12);
    }

    public void update(float delta)
    {
        player.update(delta);
    }

    public Player getPlayer()
    {
        return player;
    }

}
