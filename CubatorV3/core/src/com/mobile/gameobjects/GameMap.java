package com.mobile.gameobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by justin on 2/5/2015.
 */
public class GameMap {
    private static final int NB_CELL_WIDTH=30;
    private static final int NB_CELL_HEIGHT=10;
    private static final int NB_CELL_MAX_X=90;
    private static final int NB_CELL_MAX_Y=10;
    private static List<Tile> tiles = new ArrayList<Tile>();

    public GameMap(String map, float gameWidth, float gameHeight)
    {
        int x = 0;
        int y = 0;
        for(int i = 0;i < map.length();++i)
        {
            char c = map.charAt(i);
            if (c != '\n')
            {
                if (c != 'o')
                    tiles.add(new Tile((gameWidth / NB_CELL_WIDTH) * x, (gameHeight / NB_CELL_HEIGHT) * y, (int)(gameWidth / NB_CELL_WIDTH), (int)(gameHeight / NB_CELL_HEIGHT), 0));
                x++;
            }
            else
            {
                x=0;
                y++;
            }
        }

    }
    public boolean collides(Player player)
    {
        for (int i = 0; i < tiles.size();i++)
        {
            if (tiles.get(i).collides(player))
                return true;
        }
        return false;
    }
    public List<Tile> getTiles(){return tiles;}
}
