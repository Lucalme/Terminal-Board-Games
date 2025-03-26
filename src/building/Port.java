package building;

import board.tile.Tile;
import player.Player;

public class Port extends Building {

    public Port(Player owner,BuildingEffectType effect, Tile tile) {
        super(owner, 1, BuildingEffectType.TradingAdvantage, tile);
    }

    public static String Description() {
        return "A port allows you trade with 2 of the same type with one of your liking.";
    }
    public String toString() {
        return "⚓";
    }

}
