package building;

import player.Player;

public class Army extends Camp {
    
    private int warriors;

    public Army(Player owner, int warriors, int islandId) {
        super(owner, warriors, BuildingEffectType.MultiplyResourceProduction, islandId);
        this.warriors = warriors;
    }

    public String effect() {
        return "The army with " + warriors + " warriors is ready for battle.";
    }
}