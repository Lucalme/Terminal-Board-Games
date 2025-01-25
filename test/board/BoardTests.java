package board;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

import board.tile.Tile;
import board.Directions;

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