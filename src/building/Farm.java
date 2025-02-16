package building;

import java.util.HashMap;
import java.util.Map;

import board.resource.ResourceType;


/**
 * Represents a Farm building in the game.
 * The Farm building produces basic resources for the settlement.
 */
public class Farm extends Building {
    
    /**
     * Constructs a Farm building with a default size of 1.
     */
    public Farm() {
        super(1, BuildingEffectType.None);
    }

    public static String Description() {
        return "The farm produces basic resources.";
    }

    public static Map<ResourceType, Integer> getDefaultCost() {
        Map<ResourceType, Integer> cost = new HashMap<>();
        cost.put(ResourceType.Wood, 1);
        cost.put(ResourceType.Ore, 1);
        return cost;
    }

}
