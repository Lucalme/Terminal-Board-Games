package building;

import java.util.HashMap;
import java.util.Map;

import board.resource.ResourceType;
import player.Player;


public class Farm extends Building {
    

    public Farm(Player owner) {
        super(owner, 1, BuildingEffectType.None);
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
