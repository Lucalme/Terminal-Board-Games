package player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Game.Game;
import ares.Ares;
import board.resource.ResourceType;
import building.Army;
import building.Building;
import building.BuildingEffectType;
import demeter.Demeter;

public class Player {
    public final int numPlayer;
    protected Map<ResourceType, Integer> resources;
    private boolean hasTradingAdvantage;
    private ArrayList<Building> ownedBuildings = new ArrayList<>();
    

    public Player(int numPlayer, Game game)
    {
        this.numPlayer=numPlayer;
        //TODO: inventaire spécifique pour chaque jeu
        resources = new HashMap<ResourceType, Integer>(){{
            put(ResourceType.Ore, 10);
            put(ResourceType.Sheep, 10);
            put(ResourceType.Wheat, 10);
            put(ResourceType.Wood, 10);
        }};
        if(game instanceof Ares){
            resources.put(ResourceType.Warriors, 10);
        }
        if(game instanceof Demeter){
            resources.put(ResourceType.Thief, 0);
        }
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
        for(Building building : ownedBuildings){
            if( building.effectType == BuildingEffectType.TradingAdvantage){
                return true;
            }
        }
        return false;
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

    public ArrayList<Building> GetOwnedBuildings(){
        return ownedBuildings;
    }

    public int getTotalWarriors() {
        int totalWarriors = resources.get(ResourceType.Warriors);
        for (Building building : ownedBuildings) {
            if (building instanceof Army) {
                totalWarriors += ((Army) building).getWarriors();
            }
        }
        return totalWarriors;
    }
}