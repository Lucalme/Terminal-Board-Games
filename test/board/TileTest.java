

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import board.resource.ResourceType;
import board.tile.Tile;
import board.tile.TileType;

public class TileTest {

    @Test
    public void testTileResourceType() {
        Tile forestTile = new Tile(TileType.Forest);
        assertEquals(ResourceType.Wood, forestTile.GetResourceType(), "A forest tile should produce wood.");

        Tile mountainTile = new Tile(TileType.Mountains);
        assertEquals(ResourceType.Ore, mountainTile.GetResourceType(), "A mountain tile should produce ore.");

        Tile pastureTile = new Tile(TileType.Pastures);
        assertEquals(ResourceType.Sheep, pastureTile.GetResourceType(), "A pasture tile should produce sheep.");

        Tile fieldTile = new Tile(TileType.Fields);
        assertEquals(ResourceType.Wheat, fieldTile.GetResourceType(), "A field tile should produce wheat.");
    }
}