package com.mobile.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by justin on 2/5/2015.
 */
public class Tile extends Scrollable{
    public enum CellType{
        FLOOR, VOID
    }
    private CellType type;
    Rectangle collider;

    public Tile(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
        collider = new Rectangle(x, y,width,height);
    }

    public boolean collides(Player player)
    {
        return Intersector.overlaps(player.getBoundingCircle(), collider);
    }

    public void onRestart(float x, float scrollSpeed) {
        position.x = x;
        velocity.x = scrollSpeed;
    }
    public CellType getCellType(){return type;}
    public void setCellType(char c){
        switch (c)
        {
            case 'f':
                type = CellType.FLOOR;
                break;
            case 'o':
                default:
                    type = CellType.VOID;
                   break;
        }
    }
}
