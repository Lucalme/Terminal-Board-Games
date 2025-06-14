package player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import board.resource.ResourceType;
import building.Building;

public class Player
{
    public final int numPlayer;
    protected Map<ResourceType, Integer> resources;
    private boolean hasTradingAdvantage;
    private ArrayList<Building> ownedBuildings = new ArrayList<>();

    public Player(int numPlayer)
    {
        this.numPlayer=numPlayer;
        resources = new HashMap<ResourceType, Integer>(){{
            put(ResourceType.Ore, 5);
            put(ResourceType.Sheep, 5);
            put(ResourceType.Wheat, 5);
            put(ResourceType.Wood, 5);
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

    public String ResourcesString(){
        String res = "Your resources: ";
        for (Map.Entry<ResourceType, Integer> entry : resources.entrySet()) {
            res += "\n- " + entry.getKey() + ": " + entry.getValue();
        }
        return res;
    }

    public Map<ResourceType, Integer> getResources() {
        return resources;
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

    public boolean hasTradingAdvantage(){
        return hasTradingAdvantage;
    }

    public void setTradingAdvantage(boolean adv){
        hasTradingAdvantage = adv;
    }

    public void AddBuilding(Building building){
        ownedBuildings.add(building);
    }

    public void RemoveBuilding(Building building){
        ownedBuildings.remove(building);
    }
}