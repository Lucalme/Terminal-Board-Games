package board;

import java.util.HashMap;

import board.tile.Tile;

public class TileCollection extends HashMap<Position, Tile>{

    public TileCollection(HashMap<Position, Tile> ref){
        this.putAll(ref);
    }

    public Position[] Positions(){
        return (Position[])this.keySet().toArray();
    }

} 