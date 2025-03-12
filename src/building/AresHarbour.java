package building;

import board.tile.Tile;
import player.Player;

/**
 * The AresHarbour class represents a specialized harbour (port) in the game.
 * By default, it has the MultiplyResourceProduction effect, meaning it multiplies
 * the resource production on the island where it is built.
 * 
 * <p>
 * Inherits from {@link Building}, so it retains all building-related properties
 * (owner, size, etc.) but specifies its own effect by default.
 * </p>
 */
public class AresHarbour extends Building {

    /**
     * Constructs a new AresHarbour building with the specified parameters.
     * <br><br>
     * It automatically assigns the {@code MultiplyResourceProduction} effect
     * to enhance resource production for the owning player or island.
     * 
     * @param owner    the player who owns this harbour
     * @param size     the size or capacity of the harbour (e.g., how much space it occupies)
     * @param islandId the identifier of the island where this harbour is located
     */
    public AresHarbour(Player owner, int size, Tile tile) {
        // We pass the effect MultiplyResourceProduction to the parent constructor
        super(owner, size, BuildingEffectType.MultiplyResourceProduction, tile);
    }

    /**
     * Returns a string representation of the AresHarbour.
     * <br><br>
     * This method is often used for debugging or console display. Here, we use the
     * "⚓" character (anchor) to symbolize a port on ASCII/Unicode maps or logs.
     * 
     * @return a string that visually represents the harbour, e.g. "⚓"
     */
    @Override
    public String toString() {
        return "⚓";
    }
}

