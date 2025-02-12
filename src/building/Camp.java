package building;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Camp building in the game.
 * The Camp building enhances resource production.
 */
public class Camp extends Building {
    
    private int warriors;

    public Camp(int warriors) {
        super(warriors, getDefaultCost());
        this.warriors = warriors;
    }

    @Override
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

    private static Map<String, Integer> getDefaultCost() {
        Map<String, Integer> cost = new HashMap<>();
        cost.put("Wood", 2);
        cost.put("Ore", 3);
        return cost;
    }
}
