package building;

import board.tile.Tile;
import player.Player;

public class Army extends Building {
    
    protected int warriors;

    /** renvoie le nombre de guerriers */
    public int getWarriors(){
        return warriors;
    }

    public Army(Player owner, int warriors, BuildingEffectType effect,  Tile tile) {
        super(owner, warriors, effect, tile);
        this.warriors = warriors;
    }

    public void setWarriors(int warriors){
        this.warriors = warriors;
    }

    public String toString(){
        return "⛺";
    }

}