package building;
import board.tile.Tile;
import player.Player;


public class Farm extends Building {
    

    public Farm(Player owner,BuildingEffectType effect, Tile tile) {
        super(owner, 1, BuildingEffectType.None, tile);
    }

    public static String Description() {
        return "The farm produces basic resources.";
    }

    public String toString(){
        return "🌱";
    }
}
