package com.mobile.gameworld;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.mobile.gameobjects.Player;
import com.mobile.gameobjects.ScrollHandler;
import com.mobile.helpers.AssetLoader;

/**
 * Created by justin on 2/3/2015.
 */

public class GameWorld {
    public enum GameState{
        READY, RUNNING, GAMEOVER, MENU
    }
    private ScrollHandler scroller;
    private Player player;
    private Rectangle ground;
    private int score = 0;
    private GameState currentState;
    private int midPointY;
    private float runTime = 0;

    public GameWorld(int midPointY)
    {
        this.midPointY = midPointY;
        currentState = GameState.MENU;
        player = new Player(33,midPointY - 5, 17,12);
        scroller = new ScrollHandler(this, midPointY + 66);
        ground = new Rectangle(0,midPointY + 66, 136, 11);
    }

    public void update(float delta)
    {
        runTime += delta;
        switch(currentState) {
            case READY:
            case MENU:
                updateReady(delta);
                break;
            case RUNNING:
               default:
                updateRunning(delta);
                break;
        }
    }

    public void updateReady(float delta){
        player.updateReady(runTime);
        scroller.updateReady(delta);
    }

    public void updateRunning(float delta)
    {
        if (delta > 0.15f)
            delta = 0.15f;

        player.update(delta);
        scroller.update(delta);


        if (scroller.collides(player) && player.isAlive()){
            scroller.stop();
            player.die();
            AssetLoader.dead.play();
            currentState = GameState.GAMEOVER.GAMEOVER;
        }
        if (Intersector.overlaps(player.getBoundingCircle(), ground))
        {
            scroller.stop();
            player.die();
            player.decelerate();
            currentState = GameState.GAMEOVER.GAMEOVER;
        }
    }

    public int getMidPointY(){return midPointY;}


    public boolean isReady(){
        return currentState == GameState.READY;
    }
    public boolean isGameOver(){
        return currentState == GameState.GAMEOVER;
    }
    public void start()
    {
        currentState = GameState.RUNNING;
    }

    public void ready() {
        currentState = GameState.READY;
    }

    public void restart(){
        currentState = GameState.READY;
        score = 0;
        player.onRestart(midPointY - 5);
        scroller.onRestart();

    }

    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }


    public Player getPlayer()
    {
        return player;
    }

    public ScrollHandler getScroller()
    {
        return scroller;
    }

    public int getScore(){
        return score;
    }

    public void addScore(int increment){
        score += increment;
    }

}
