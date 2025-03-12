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
    
    private int warriors;

    public Camp(Player owner, int warriors, Tile tile) {
        super(owner, warriors, BuildingEffectType.MultiplyResourceProduction, tile);
        this.warriors = warriors;
    }

    public String effect() {
        return "The camp improves resource production and houses " + warriors + " warriors.";
    }

    public void addWarriors(int count) {
        this.warriors += count;
        this.size = warriors;
    }

    public void removeWarriors(int count) {
        this.warriors = Math.max(0, this.warriors - count);
        this.size = warriors;
    }

    public static Map<ResourceType, Integer> getDefaultCost(){
        //TODO: vérifier les coûts corrects pour un camp
        Map<ResourceType, Integer> cost = new HashMap<>();
        cost.put(ResourceType.Wood, 10);
        cost.put(ResourceType.Ore, 5);
        return cost;
    }

    public String toString() {
        return "🪖";
    }
}
