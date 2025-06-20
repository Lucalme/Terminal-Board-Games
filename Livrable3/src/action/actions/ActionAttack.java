package action.actions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Game.Game;
import action.Action;
import action.util.IO;
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
    private Player winner, looser;
    private Player targetOwner;

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
        Army looserArmy = attackerScore < defenderScore ? attacker : defender;
        winner = attackerScore < defenderScore ? defender.owner : attacker.owner;
        looser = attackerScore < defenderScore ? attacker.owner : defender.owner;
        int looserwarriors = looserArmy.getWarriors();
        if(looserwarriors == 1){
            target.SetBuilding(null);
            destroyedBuilding = true;
        }else{
            looserArmy.setWarriors(looserwarriors - 1);
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
            filter(e -> e.getValue().numPlayer == playerid && (e.getKey() instanceof  Army || e.getKey() instanceof Camp)).
            map(e -> e.getKey()).collect(Collectors.toList());
        List<Integer> checkedIslands = new ArrayList<>();
        for(Building b : playerWarBuildings){
            if(checkedIslands.contains(b.islandId)){
                continue;
            }
            checkedIslands.add(b.islandId);
            List<Building> sameIslandAdversaryWarBuilding = buildings.entrySet().stream().
                filter(e -> e.getValue().numPlayer != playerid && e.getKey().islandId == b.islandId && (e.getKey() instanceof  Army || e.getKey() instanceof Camp)).
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
        || target.GetBuilding() == null || (target.GetBuilding() instanceof Army && target.GetBuilding() instanceof Camp) || target.GetBuilding().owner == player
        || baseCamp.GetBuilding() == null || (baseCamp.GetBuilding() instanceof Army && baseCamp.GetBuilding() instanceof Camp) || baseCamp.GetBuilding().owner != player){
            return false;
        }
        targetOwner = target.GetBuilding().owner;
        return true;
    }

    public String Description(){
        return source.toString() + " attaque " + targetOwner.toString() + "! "
        + (winner.toString() + " a gagné la bataille! " + (destroyedBuilding ? "Le batiment a été détruit!" : looser.toString() + " a perdu un guerrier!"));
    }
}
