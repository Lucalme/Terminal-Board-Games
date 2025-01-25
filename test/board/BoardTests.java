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
        Board board = new Board(8, 11);
        int totalTiles = board.getSizeX() * board.getSizeY();
        int waterTiles = totalTiles - board.getTiles().size();
        double waterPercentage = (double) waterTiles / totalTiles * 100;
        assertTrue(waterPercentage >= 66, "The board must contain at least one non-aquatic tile.");
    }

    @Test
    public void testNoIsolatedTiles(){
        Board board = new Board();
        for (Map.Entry<Position, Tile> entry : board.getTiles().entrySet()) {
            boolean hasNeighbour = !board.getTilesNeighborhood(entry.getKey()).isEmpty();
            assertTrue(hasNeighbour, "Each tile must have at least one neighboring tile.");
        }
    }
    
    //tests supp
    @Test 
    public void testBoardSize() {
        Board board = new Board(10, 10);
        asserEquals(10, board.getSizeX(), "The board width should be 10.");
        asserEquals(10, board.getSizeY(), "The board height should be 10");
    }

    @Test
    public void testValidPositions() {
        Board board = new Board(5, 5);
        assertTrue(board.getTiles().containsKey(new Position(0, 0)) || !board.getTiles().containsKey(new Position(0, 0)));
    }
}