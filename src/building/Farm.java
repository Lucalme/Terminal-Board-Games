package building;

import java.util.HashMap;
import java.util.Map;


/**
 * Represents a Farm building in the game.
 * The Farm building produces basic resources for the settlement.
 */
public class Farm extends Building {
    
    /**
     * Constructs a Farm building with a default size of 1.
     */
    public Farm() {
        super(1, getDefaultCost());
    }

    /**
     * Defines the effect of the Farm building.
     * 
     * @return a description of the farm's effect
     */
    @Override
    public String effect() {
        return "The farm produces basic resources.";
    }
    private static Map<String, Integer> getDefaultCost() {
        Map<String, Integer> cost = new HashMap<>();
        cost.put("Wood", 1);
        cost.put("Ore", 1);

        return cost;

    }

}
