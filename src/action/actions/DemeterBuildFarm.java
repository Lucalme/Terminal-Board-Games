package action.actions;
import building.BuildingEffectType;
import building.Farm;

import java.util.HashMap;
import java.util.Map;

import Game.Game;
import action.ActionBuild;
import board.resource.ResourceType;
import board.tile.Tile;
import player.Player;
public class DemeterBuildFarm  extends ActionBuild {
    
    public DemeterBuildFarm(Player source, Tile tile){
        super(source, new Farm(source, BuildingEffectType.None, tile), tile);
    }
    public static HashMap<ResourceType, Integer> Cost() {
        return new HashMap<>(){{
            //TODO: Vérifier ces valeurs.
            put(ResourceType.Wood, 1);
            put(ResourceType.Ore, 1);
        }};
    }

    public static boolean isPossible(Player player, Game game){
        //TODO: vérifier s'il y a d'autres conditions. nécessite d'autres buildings?
        HashMap<ResourceType, Integer> playerResources = (HashMap<ResourceType, Integer>) player.getResources();
        for(Map.Entry<ResourceType, Integer> entry : Cost().entrySet()){
            if(playerResources.get(entry.getKey()) < entry.getValue()){
                return false;
            }
        }
        return true;
    }

    public String Description() {
        return source.toString() + " a construit une Farme sur l'île n°"+islandId;
    }
    public boolean CheckInstancePossible(Player player, Game game){
        return TileIsEmpty(tile) && TileHasWaterNeighbour(tile, player, game);
    }   
}
