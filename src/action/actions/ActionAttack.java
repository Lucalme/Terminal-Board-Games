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
    public final Tile baseCamp;
    private boolean destroyedBuilding = false;

    public ActionAttack(Player player, Tile baseCamp, Tile target){
        super(player, true);
        this.target = target;
        this.baseCamp = baseCamp;
    }

    public void Effect() {
        Army attacker = (Army) baseCamp.GetBuilding();
        Army defender = (Army) target.GetBuilding();
        int attackerDices = attacker.getWarriors() < 4 ? 1 : attacker.getWarriors() < 7 ? 2 : 3;
        int defenderDices = defender.getWarriors() < 4 ? 1 : defender.getWarriors() < 7 ? 2 : 3;
        int attackerScore = 0;
        int defenderScore = 0;
        for(int i = 0; i < attackerDices; i++){
            attackerScore += (int)(Math.random() * 10) + 1; //Dé à 10 faces
        }
        for(int i = 0; i < defenderDices; i++){
            defenderScore += (int)(Math.random() * 10) + 1;
        }
        Army looser = attackerScore > defenderScore ? attacker : defender;
        int looserwarriors = looser.getWarriors();
        if(looserwarriors == 1){
            target.SetBuilding(null);
            destroyedBuilding = true;
        }else{
            looser.setWarriors(looserwarriors - 1);
        }
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
        if( target.GetIslandID() != baseCamp.GetIslandID()
        || target.GetBuilding() == null || (target.GetBuilding().getClass() != Army.class && target.GetBuilding().getClass() != Camp.class) || target.GetBuilding().owner == player
        || baseCamp.GetBuilding() == null || (baseCamp.GetBuilding().getClass() != Army.class && baseCamp.GetBuilding().getClass() != Camp.class) || baseCamp.GetBuilding().owner != player){
            return false;
        }
        return true;
    }

    public String Description(){
        return source.toString() + " attaque " + target.toString() + "!";
    }
}
