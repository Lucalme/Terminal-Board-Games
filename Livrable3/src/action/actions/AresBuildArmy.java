package action.actions;

import java.util.HashMap;

import Game.Game;
import action.ActionBuild;
import board.resource.ResourceType;
import board.tile.Tile;
import building.Army;
import building.Building;
import building.BuildingEffectType;
import building.Camp;
import player.Player;

public class AresBuildArmy extends ActionBuild{

    public final int nbOfWarriors;
    private static final HashMap<ResourceType, Integer> ArmyCost = new HashMap<>(){{
        put(ResourceType.Wood,1);
        put(ResourceType.Sheep, 1);
        put(ResourceType.Wheat, 1);
    }};
    private static final HashMap<ResourceType, Integer> CampCost = new HashMap<>(){{
        put(ResourceType.Sheep, 1);
        put(ResourceType.Wheat, 1);
        put(ResourceType.Wood,3);
        put(ResourceType.Ore, 3);
    }};

    public AresBuildArmy(Player builder, Tile tile, int nbOfWarriors) {
        super(builder, ArmyOrCamp(builder, nbOfWarriors, tile), tile);
        this.nbOfWarriors = nbOfWarriors;
    }
        
    private static Building ArmyOrCamp(Player builder , int nbOfWarriors, Tile tile ){
        if(nbOfWarriors < 6){
            return new Army(builder, nbOfWarriors, BuildingEffectType.None,  tile);
        }else{
            return new Camp(builder, nbOfWarriors, tile);
        }
    }

    @Override
    public boolean CheckInstancePossible(Player player, Game game) {
        return TileIsEmpty(tile) && player.getResources().get(ResourceType.Warriors) >= nbOfWarriors && PlayerCanAfford(player, (building instanceof Camp ? CampCost  :ArmyCost ));
    }

    @Override
    public String Description() {
        return source.toString() +" a créé "+ (building instanceof Camp ? "un camp" : "une armée")+" de "+nbOfWarriors+" guerriers sur l'ile n°"+islandId;
    }

    @Override
    public void Effect(){
        if(building instanceof Camp){
            source.removeResource(ResourceType.Wood, 3);
            source.removeResource(ResourceType.Sheep, 1);
            source.removeResource(ResourceType.Wheat, 1);
            source.removeResource(ResourceType.Ore, 3);
        }else{
            source.removeResource(ResourceType.Wood, 1);
            source.removeResource(ResourceType.Sheep, 1);
            source.removeResource(ResourceType.Wheat, 1);
        }
        source.removeResource(ResourceType.Warriors, nbOfWarriors);
        tile.SetBuilding(building);
    }



    public static boolean isPossible(Player player, Game game){
        return player.getResources().get(ResourceType.Warriors) > 0 && PlayerCanAfford(player, ArmyCost);  
    }
    
}
