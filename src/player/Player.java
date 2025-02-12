package player;

import java.util.HashMap;
import java.util.Map;

import board.resource.ResourceType;

import building.Building;

public class Player
{
    public final int numPlayer;
    //protected int warriorStock=30;
    protected Map<ResourceType, Integer> resources;

    public Player(int numPlayer)
    {
        this.numPlayer=numPlayer;
        resources = new HashMap<ResourceType, Integer>(){{
            put(ResourceType.Ore, 0);
            put(ResourceType.Sheep, 0);
            put(ResourceType.Wheat, 0);
            put(ResourceType.Wood, 0);
            put(ResourceType.Warriors, 30);
        }};
    }

    public void addResource(ResourceType type, int amount) {
        resources.put(type, resources.getOrDefault(type, 0) + amount);
    }
    
    public void showResources() {
        System.out.println(" Your Resources:");
        for (Map.Entry<ResourceType, Integer> entry : resources.entrySet()) {
            System.out.println("- " + entry.getKey() + ": " + entry.getValue());
        }
    }

    public void removeResource(ResourceType type, int amount) {
        if (resources.containsKey(type)) {
            int currentAmount = resources.get(type);
            int newAmount = Math.max(0, currentAmount - amount); // Ensure it doesn't go negative
            resources.put(type, newAmount); // Always keep the resource in the map, even if it's 0
        } else {
            System.out.println("You don't have any " + type + " to remove.");
        }
    }

    @Override
    public String toString()
    {
        return "Player "+numPlayer;
        
    }
   




}