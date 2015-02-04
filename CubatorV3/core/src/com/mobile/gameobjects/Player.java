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
        acceleration = new Vector2(0,460);
        realSWidth = Gdx.graphics.getWidth();
        realSHeight = Gdx.graphics.getHeight();

    }
    public void update(float delta){
        velocity.add(acceleration.cpy().scl(delta));

        if (velocity.y > 200) {
            velocity.y = 200;
        }

        position.add(velocity.cpy().scl(delta));

        // Rotate counterclockwise
        if (velocity.y < 0) {
            rotation -= 600 * delta;

            if (rotation < -20) {
                rotation = -20;
            }
        }

        // Rotate clockwise
        if (isFalling()) {
            rotation += 480 * delta;
            if (rotation > 90) {
                rotation = 90;
            }

        }
    }

    public void onClick(int x, int y)
    {
       velocity.y -= 140;
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


    public boolean isFalling(){
        return velocity.y > 110;
    }

    public boolean shouldntFlap(){
        return velocity.y > 70;
    }

    public float getX(){return position.x;}

    public float getY(){return position.y;}

    public int getWidth(){return width;}

    public int getHeight(){return height;}

    public float getRotation(){return rotation;}
}
