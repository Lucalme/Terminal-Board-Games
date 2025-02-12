package player;

import java.util.HashMap;
import java.util.Map;

import board.resource.ResourceType;
import building.Building;

public class Player
{
    private int numPlayer;
    private int warriorStock=30;
    private Map<ResourceType, Integer> resources;

   /*  Player(int numPlayer)
    {
        this.numPlayer=numPlayer;
        resources = new HashMap<>();
        for (ResourceType type : ResourceType.values()) {
            resources.put(type, 0);
        }
    }*/
    public Player()
    {

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