package building;

import board.tile.Tile;
import player.Player;

public class Army extends Building {
    
    private int warriors;

    public Army(Player owner, int warriors, BuildingEffectType effect,  Tile tile) {
        super(owner, warriors, effect, tile);
        this.warriors = warriors;
    }

    public String effect() {
        return "The army with " + warriors + " warriors is ready for battle.";
    }

    public String toString(){
        return "⛺";
    }
}