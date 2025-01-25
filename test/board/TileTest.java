package board.tile;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import board.resource.ResourceType;

public class TileTest {

    @Test
    public void testTileResourceType() {
        Tile forestTile = new Tile(TileType.FOREST);
        assertEquals(ResourceType.WOOD, forestTile.getResourceType(), "A forest tile should produce wood.");

        Tile mountainTile = new Tile(TileType.MOUNTAIN);
        assertEquals(ResourceType.ORE, mountainTile.getResourceType(), "A mountain tile should produce ore.");

        Tile pastureTile = new Tile(TileType.PASTURE);
        assertEquals(ResourceType.SHEEP, pastureTile.getResourceType(), "A pasture tile should produce sheep.");

        Tile fieldTile = new Tile(TileType.FIELD);
        assertEquals(ResourceType.WHEAT, fieldTile.getResourceType(), "A field tile should produce wheat.");
    }
}