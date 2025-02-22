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

    /**
     * Constructeur abstrait de Building
     * @param owner le propiétaire du bâtiment, final et accessible publiquement en lecture
     * @param size la dimension du bâtiment
     * @param effectType le type d'effet du bâtiment, le bâtiment peut augmenter la production de la tile sur laquelle il est ou donner un avantage d'échange à son propriétaire
     * @param islandId l'identifiant de l'île sur laquelle le bâtiment est construit 
     */
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

    public abstract String toString();
   
}