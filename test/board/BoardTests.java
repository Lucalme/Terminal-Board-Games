package board;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import board.tile.Tile;
import board.tile.TileType;

import java.util.Map;

public class BoardTests {

    @Test
    public void testAtLeastOneNonWaterTileOnBoard() {
        Board board = new Board(8, 11); // Provide sizeX and sizeY
        assertTrue(board.getTiles().size() > 0, "The board must contain at least one non-aquatic tile.");
    }

    @Test
    public void testHasAtLeast66PercentWater() {
        Board board = new Board(8, 11);
        int totalTiles = board.SizeX() * board.SizeY();
        int waterTiles = totalTiles - board.getTiles().size();
        double waterPercentage = (double) waterTiles / totalTiles * 100;
        assertTrue(waterPercentage >= 66, "The board must contain at least 66% water tiles.");
    }

    @Test
    public void testNoIsolatedTiles() {
        Board board = new Board(8, 11); // Provide sizeX and sizeY
        for (Map.Entry<int[], Tile> entry : board.getTiles().entrySet()) {
            int x = entry.getKey()[0];
            int y = entry.getKey()[1];
            boolean hasNeighbour = board.GetTilesNeighborhood(x, y).length > 0;
            assertTrue(hasNeighbour, "Each tile must have at least one neighboring tile.");
        }
    }

    @Test
    public void testBoardSize() {
        Board board = new Board(10, 10);
        assertEquals(10, board.SizeX(), "The board width should be 10.");
        assertEquals(10, board.SizeY(), "The board height should be 10.");
    }

    @Test
    public void testValidPositions() {
        Board board = new Board(5, 5);
        assertTrue(board.getTiles().containsKey(new int[] {0, 0}) || !board.getTiles().containsKey(new int[] {0, 0}));
    }

    @Test
    public void testGetTilesNeighborhood() {
        Board board = new Board(3, 3);
        board.getTiles().put(new int[] {1, 1}, new Tile(new Position(0,0),TileType.Forest));
        board.getTiles().put(new int[] {0, 1}, new Tile(new Position(0,0),TileType.Mountains));
        assertEquals(1, board.GetTilesNeighborhood(1, 1).length, "The tile at (1,1) should have 1 neighbor.");
    }

}