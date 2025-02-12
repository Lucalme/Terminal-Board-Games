package building;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Port building in the game.
 * The Port building enables advantageous resource exchanges.
 */
public class Port extends Building {

    public Port() {
        super(1, getDefaultCost());
    }

    @Override
    public String effect() {
        return "The port allows advantageous resource exchanges.";
    }

    public static boolean canBeBuilt(boolean isAdjacentToSea) {
        return isAdjacentToSea;
    }

    private static Map<String, Integer> getDefaultCost() {
        Map<String, Integer> cost = new HashMap<>();
        cost.put("Wood", 1);
        cost.put("Sheep", 2);
        return cost;
    }
}
