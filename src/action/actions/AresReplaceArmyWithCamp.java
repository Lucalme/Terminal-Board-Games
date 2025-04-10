package action.actions;
import java.util.HashMap;
import Game.Game;
import action.ActionBuild;
import action.util.IO;
import board.resource.ResourceType;
import board.tile.Tile;
import building.Army;
import building.Building;
import building.Camp;
import player.Player;

public class AresReplaceArmyWithCamp extends ActionBuild {

    public AresReplaceArmyWithCamp(Player source, Tile tile){
        super(source, new Camp(source, nbOfWarriors(tile), tile), tile);
    }

    public static HashMap<ResourceType, Integer> Cost() {
        return new HashMap<>(){{
            //TODO: Vérifier ces valeurs.
            put(ResourceType.Wood, 2);
            put(ResourceType.Ore, 3);
        }};
    }

    private static int nbOfWarriors(Tile tile){
        if(tile != null && tile.GetBuilding() != null && tile.GetBuilding() instanceof Army){
            return ((Army)tile.GetBuilding()).getWarriors();
        }
        return 0; 
    }

    public void Effect() {
        source.RemoveBuilding(tile.GetBuilding());
        super.Effect();
    }


    public static boolean isPossible(Player player, Game game){
        //TODO: vérifier s'il y a d'autres conditions. nécessite d'autres buildings?
        boolean atLeastOneArmy = false;
        for(Building b : player.GetOwnedBuildings()){
            if(b instanceof Army && !(b instanceof Camp)){
                atLeastOneArmy = true;
                break;
            }
        }
        return PlayerCanAfford(player, Cost()) && atLeastOneArmy;
    }

    public String Description() {
        return source.toString() + " remplace une armée par un camp sur l'île n°"+islandId;
    }
    
    public boolean CheckInstancePossible(Player player, Game game){
        return (tile.GetBuilding() != null) && (tile.GetBuilding().owner == player) && (tile.GetBuilding() instanceof Army) && !(tile.GetBuilding() instanceof Camp);
    }   

}
