package building;

import java.util.HashMap;
import java.util.Map;

import board.resource.ResourceType;
import player.Player;

public abstract class Building {

    protected int size;
    public final BuildingEffectType effectType;
    public final Player owner;
    public final int islandId;

    public Building(Player owner, int size, BuildingEffectType effectType, int islandId) {
        this.owner = owner;
        this.size = size;
        this.effectType = effectType;
        this.islandId = islandId;
    }

    public int getSize() {
        return size;
    }

    public HashMap<ResourceType, Integer> ResourceEffect(HashMap<ResourceType, Integer> resources) {
        if(effectType == BuildingEffectType.MultiplyResourceProduction){
            HashMap<ResourceType, Integer> newResources = new HashMap<>();
            for (Map.Entry<ResourceType, Integer> entry : resources.entrySet()) {
                newResources.put(entry.getKey(), entry.getValue() * 2);
            }
            return newResources;
        } else {
            return resources; 
        }
    }
   
}