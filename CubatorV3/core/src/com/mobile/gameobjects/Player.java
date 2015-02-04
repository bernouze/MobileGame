package com.mobile.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mobile.helpers.AssetLoader;

/**
 * Created by justin on 2/3/2015.
 */
public class Player {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private int realSWidth;
    private int realSHeight;
    private Circle boundingCircle;
    private float originalY;
    private float rotation;
    private int width;
    private int height;
    private boolean isAlive;


    public Player(float x, float y, int width, int height)
    {
        isAlive = true;
        this.width = width;
        this.height = height;
        position = new Vector2(x,y);
        originalY = y;
        velocity = new Vector2(0,0);
        acceleration = new Vector2(0,460);
        realSWidth = Gdx.graphics.getWidth();
        realSHeight = Gdx.graphics.getHeight();
        boundingCircle = new Circle();
    }

    public void onRestart(int y)
    {
        rotation = 0;
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = 460;
        isAlive = true;
    }

    public void update(float delta){
        velocity.add(acceleration.cpy().scl(delta));

        if (velocity.y > 200) {
            velocity.y = 200;
        }

        if (position.y < -13) {
            position.y = -13;
            velocity.y = 0;
        }

        position.add(velocity.cpy().scl(delta));
        boundingCircle.set(position.x + 9, position.y + 6, 6.5f);
        // Rotate counterclockwise
        if (velocity.y < 0) {
            rotation -= 600 * delta;

            if (rotation < -20) {
                rotation = -20;
            }
        }

        // Rotate clockwise
        if (isFalling() || !isAlive) {
            rotation += 480 * delta;
            if (rotation > 90) {
                rotation = 90;
            }

        }
    }

    public void updateReady(float runTime) {
        position.y = 2 * (float) Math.sin(7 * runTime) + originalY;
    }

    public void onClick(int x, int y)
    {
        if (isAlive) {
            AssetLoader.flap.play();
            velocity.y -= 140;
        }
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
        return velocity.y > 70 || !isAlive;
    }
    public boolean isAlive(){return isAlive;}

    public float getX(){return position.x;}

    public float getY(){return position.y;}

    public int getWidth(){return width;}

    public int getHeight(){return height;}

    public float getRotation(){return rotation;}

    public Circle getBoundingCircle(){
        return boundingCircle;
    }

    public void die()
    {
        isAlive = false;
        velocity.y = 0;
    }

    public void decelerate(){
        acceleration.y = 0;
    }

}
