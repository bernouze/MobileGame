package com.mobile.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by justin on 2/3/2015.
 */
public class Player {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private int realSWidth;
    private int realSHeight;

    private float rotation;
    private int width;
    private int height;
    public Player(float x, float y, int width, int height)
    {
        this.width = width;
        this.height = height;
        position = new Vector2(x,y);
        velocity = new Vector2(0,0);
        acceleration = new Vector2(0,400);
        realSWidth = Gdx.graphics.getWidth();
        realSHeight = Gdx.graphics.getHeight();

    }
    public void update(float delta){
        if (velocity.y < 0)
            acceleration.y = 400;
        else {
            acceleration.y = 0;
            velocity.y = 0;
        }
        velocity.add(acceleration.cpy().scl(delta));
        if (velocity.y > 200)
            velocity.y = 200;

        position.add(velocity.cpy().scl(delta));
    }

    public void onClick(int x, int y)
    {
        Gdx.app.log("PLayerOnClick", "click x = " + x);
        Gdx.app.log("PlayerOnClick", "click y = " + y);

        if (x < realSWidth / 2 && y < realSHeight / 2)
            velocity.y = -140;
    }
    public void onTouch(int x, int y)
    {
        if (x < realSWidth / 2 && y > realSHeight / 2)
            velocity.x = -30;
        else if (x > realSWidth / 2 && y > realSHeight / 2)
            velocity.x = 30;
    }
    public void resetMove()
    {
        velocity.x = 0;
    }

    public float getX(){return position.x;}

    public float getY(){return position.y;}

    public int getWidth(){return width;}

    public int getHeight(){return height;}

    public float getRotation(){return rotation;}
}
