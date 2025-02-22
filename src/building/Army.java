package building;

import player.Player;

public class Army extends Building {
    
    private int warriors;

    public Army(Player owner, int warriors, BuildingEffectType effect,  int islandId) {
        super(owner, warriors, effect, islandId);
        this.warriors = warriors;
    }

    public String effect() {
        return "The army with " + warriors + " warriors is ready for battle.";
    }

    public String toString(){
        return "⛺";
    }
}