package action.actions;

import Game.Game;
import action.Action;
import board.resource.ResourceType;
import board.tile.Tile;
import building.Army;
import building.Building;
import building.Camp;
import player.Player;

public class AresAddWarriorToBuilding extends Action{

    private final int amount;
    private final Tile tile;

    public AresAddWarriorToBuilding(Player player, int amount, Tile tile) { 
        super(player, true);
        this.amount = amount;
        this.tile = tile;
    }
    
    @Override
    public boolean CheckInstancePossible(Player player, Game game) {
        return tile.GetBuilding() != null &&
                (tile.GetBuilding() instanceof Army || tile.GetBuilding() instanceof Camp) && 
                tile.GetBuilding().owner == player &&
                player.getResources().get(ResourceType.Warriors) >= amount;
    }

    public static boolean isPossible(Player player, Game game){
        if(player.getResources().get(ResourceType.Warriors) <= 0){
            return false;
        }
        boolean atLeastOneCampOrArmy = false;
        for(Building b : player.GetOwnedBuildings()){
            if(b instanceof Army || b instanceof Camp){
                atLeastOneCampOrArmy = true;
                break;
            }
        }
        return atLeastOneCampOrArmy;
    }

    @Override
    public void Effect() {
        ((Army)tile.GetBuilding()).setWarriors(((Army)tile.GetBuilding()).getWarriors() + amount);
        source.removeResource(ResourceType.Warriors, amount);
    }

    @Override
    public String Description() {
        return source.toString() + " a ajouté " + amount + " guerriers à son "+ (tile.GetBuilding() instanceof Camp ? "camp" : "armée" ) +" sur l'île n°" + tile.GetIslandID();
    }
    

}
