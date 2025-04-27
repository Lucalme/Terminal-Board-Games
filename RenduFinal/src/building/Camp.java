package building;

import java.util.HashMap;
import java.util.Map;

import board.resource.ResourceType;
import board.tile.Tile;
import player.Player;

/**
 * Represents a Camp building in the game.
 * The Camp building enhances resource production.
 */
public class Camp extends Army{
    

    public Camp(Player owner, int warriors, Tile tile) {
        super(owner, warriors, BuildingEffectType.MultiplyResourceProduction, tile);
        this.warriors = warriors;
    }

    public String toString() {
        return "⛺";
    }
}
