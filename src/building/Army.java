package building;

import player.Player;

public class Army extends Camp {
    
    private int warriors;

    public Army(Player owner, int warriors) {
        super(owner, warriors, BuildingEffectType.MultiplyResourceProduction);
        this.warriors = warriors;
    }

    public String effect() {
        return "The army with " + warriors + " warriors is ready for battle.";
    }
}