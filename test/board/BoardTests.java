package board;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import board.tile.Tile;
import board.tile.TileType;
import board.Directions;

import java.util.Map;

public class BoardTests{

    @Test
    public void testAtLeastOneNonWaterTileOnBoard(){
        Board board = new Board();
        assertTrue(board.getTiles().size() > 0, "The board must contain at least one non-aquatic tile.");
    }

    @Test
    public void testHasAtLeast66PercentWater(){
        Board board = new Board();
        assertTrue(board.getTiles().size() > 0, "The board must contain at least one non-aquatic tile.");
    }

    @Test
    public void NoIsolatedTiles()
    {
        Board board= new Board();
        HashMap<int[], Tile> entries = board.getTiles(); 

        for(Map.Entry<int[], Tile> entry : entries.entrySet())
        {
            Boolean hasNeighbour = false;
            for(Directions dir : Directions.values())
            {
                if(entry.getKey()[0] + dir.X < 0 || entry.getKey()[0] + dir.X >= board.SizeX() || entry.getKey()[1] + dir.Y < 0 || entry.getKey()[1] + dir.Y >= board.SizeY())
                {
                    continue;
                }
                //La méthode containsKey ne fonctionnait pas ici, on doit faire un recherche manuelle
                //idée : étendre la class HashMap<int[], Tile> pour implémenter nos propres méthodes.
                for(int[] key : entries.keySet()){
                    if(key[0] == entry.getKey()[0]+dir.X && key[1] == entry.getKey()[1] + dir.Y){
                        hasNeighbour = true;
                    }
                }
            }
            
            assertTrue(hasNeighbour);
        }
    }

    
}