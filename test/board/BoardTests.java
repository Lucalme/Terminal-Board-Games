package board;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

import board.tile.Tile;

public class BoardTests{

    @Test
    public void AtLeastOneNonWaterTileOnBoard(){
        Board board = new Board();
        assertTrue(board.getTiles().size() > 0);
    }

    @Test
    public void HasAtLeast66PercentWater(){
        Board board = new Board(8,11);
        assertTrue(board.getTiles().size() <= 29);
    }

    @Test
    public void NoIsolatedTiles()
    {
        Board board= new Board();
        for(Map.Entry<int[], Tile> entry : board.getTiles().entrySet())
        {
            Boolean hasNeighbour = false;
            for(Directions dir : Directions.values())
            {
                if(entry.getKey()[0] + dir.x < 0 || entry.getKey()[0] + dir.x >= board.SizeX() || entry.getKey()[1] + dir.y < 0 || entry.getKey()[1] + dir.y >= board.SizeY())
                {
                    continue;
                }
               if(board.getTiles().containsKey(new int[] {entry.getKey()[0] + dir.x, entry.getKey()[1] + dir.y}))
               {
                   hasNeighbour = true;
                   break;
               }
            }
            assertTrue(hasNeighbour);
        }

    }
}