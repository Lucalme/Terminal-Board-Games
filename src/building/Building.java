package building;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an abstract Building in the game.
 * Buildings have a size and can have specific effects that are implemented in subclasses.
 * 
 * Subclasses should provide specific implementations for the effect method.
 */
public abstract class Building {

    /**
     * The size of the building, which can represent its dimensions, capacity, or any other relevant attribute.
     */
    protected int size;
    /** The construction cost of the building in terms of resources */
    protected Map<ResourceType, Integer> constructionCost;

    /**
     * Constructs a Building with the specified size and cost.
     * 
     * @param size the size of the building
     * @param cost the construction cost of the building
     */
    public Building(int size, Map<ResourceType, Integer> cost) {
        this.size = size;
        this.constructionCost = new HashMap<>(cost);
    }

    /**
     * Gets the size of the building.
     * 
     * @return the size of the building
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets the construction cost of the building.
     * @return the construction cost as a map of resource types to amounts
     */
    public Map<ResourceType, Integer> getConstructionCost() {
        return new HashMap<>(constructionCost);
    }

    /**
     * Checks if the provided resources are sufficient to construct the building.
     * @param availableResources the resources available to the player
     * @return true if the player has enough resources, false otherwise
     */
    public boolean canConstruct(Map<String, Integer> availableResources) {
        for (Map.Entry<ResourceType, Integer> entry : constructionCost.entrySet()) {
            ResourceType resource = entry.getKey();
            int requiredAmount = entry.getValue();
            
            // Convertir le String en ResourceType avant de comparer
            int availableAmount = availableResources.getOrDefault(resource.name(), 0);
            
            if (availableAmount < requiredAmount) {
                return false;
            }
        }
        return true;
    }
    

    /**
     * Abstract method to define the effect of the building.
     * @return a description of the building's effect
     */
    public abstract String effect();
}
