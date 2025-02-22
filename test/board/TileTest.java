import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import board.resource.ResourceType;
import board.Position;
import board.tile.Tile;
import board.tile.TileType;

public class TileTest {

    @Test
    public void testTileResourceType() {
        Tile forestTile = new Tile(new Position(0,0), TileType.Forest);
        assertEquals(ResourceType.Wood, forestTile.GetResourceType(), "A forest tile should produce wood.");

        Tile mountainTile = new Tile(new Position(0,0), TileType.Mountains);
        assertEquals(ResourceType.Ore, mountainTile.GetResourceType(), "A mountain tile should produce ore.");

        Tile pastureTile = new Tile(new Position(0,0), TileType.Pastures);
        assertEquals(ResourceType.Sheep, pastureTile.GetResourceType(), "A pasture tile should produce sheep.");

        Tile fieldTile = new Tile(new Position(0,0), TileType.Fields);
        assertEquals(ResourceType.Wheat, fieldTile.GetResourceType(), "A field tile should produce wheat.");
    }
}