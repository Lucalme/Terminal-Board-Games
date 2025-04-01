package building;

import java.util.HashMap;
import java.util.Map;

import board.resource.ResourceType;
import board.tile.Tile;
import player.Player;

public abstract class Building {

    protected int size;
    public final BuildingEffectType effectType;
    public final Player owner;
    public final int islandId;
    public final Tile tile;

    /**
     * Constructeur abstrait de Building
     * @param owner le propiétaire du bâtiment, final et accessible publiquement en lecture
     * @param size la dimension du bâtiment
     * @param effectType le type d'effet du bâtiment, le bâtiment peut augmenter la production de la tile sur laquelle il est ou donner un avantage d'échange à son propriétaire
     * @param islandId l'identifiant de l'île sur laquelle le bâtiment est construit 
     */
    public Building(Player owner, int size, BuildingEffectType effectType, Tile tile) {
        this.owner = owner;
        this.size = size;
        this.effectType = effectType;
        this.islandId = tile.GetIslandID();
        this.tile = tile;
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

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " at position (" + tile.position.x + ", " + tile.position.y + ")";
    }
   
}