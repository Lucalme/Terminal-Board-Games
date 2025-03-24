package action.actions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Game.Game;
import action.Action;
import board.Position;
import board.resource.ResourceType;
import board.tile.Tile;
import building.Army;
import building.Building;
import building.Camp;
import player.Player;

public class ActionAttack extends Action {
    
    public final Tile target;

    public ActionAttack(Player player, Tile target){
        super(player, true);
        this.target = target;
    }

    public void Effect() {
        //TODO : implémenter l'attaque
    }

    public static HashMap<ResourceType, Integer>  Cost(){
        return null;
    }

    /** true si le player a au moins un camp ou une armée sur la même ile qu'un camp ou une armée adverse. */
    public static boolean isPossible(Player player, Game game){
        int playerid = player.numPlayer;
        HashMap<Building, Player> buildings = game.board.getBuildings();
        List<Building> playerWarBuildings = buildings.entrySet().stream().
            filter(e -> e.getValue().numPlayer == playerid && (e.getKey().getClass() ==  Army.class || e.getKey().getClass() == Camp.class)).
            map(e -> e.getKey()).collect(Collectors.toList());
        List<Integer> checkedIslands = new ArrayList<>();
        for(Building b : playerWarBuildings){
            if(checkedIslands.contains(b.islandId)){
                continue;
            }
            checkedIslands.add(b.islandId);
            List<Building> sameIslandAdversaryWarBuilding = buildings.entrySet().stream().
                filter(e -> e.getValue().numPlayer != playerid && e.getKey().islandId == b.islandId && (e.getKey().getClass() ==  Army.class || e.getKey().getClass() == Camp.class)).
                map(e -> e.getKey()).collect(Collectors.toList());
            if(sameIslandAdversaryWarBuilding.size() > 0){
                return true;
            }
        }
        return false;
        
    }

    /** true si le player a au moins un camp ou une armée sur la même ile que la cible. */
    public boolean CheckInstancePossible(Player player, Game game){
        int targetisland = target.GetIslandID();
        if(target.GetBuilding() == null || (target.GetBuilding().getClass() != Army.class && target.GetBuilding().getClass() != Camp.class)){
            return false;
        }
        for(Building b: source.GetOwnedBuildings()){
            if(b.islandId == targetisland && (b.getClass() == Army.class || b.getClass() == Camp.class)){
                return true;
            }   
        }
        return false;
    }

    public String Description(){
        return source.toString() + " attaque " + target.toString() + "!";
    }
}
