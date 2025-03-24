package action.actions;
import java.util.HashMap;
import java.util.Map;

import Game.Game;
import action.Action;
import board.Position;
import board.resource.ResourceType;
import building.Army;
import building.Building;
import building.Camp;
import player.Player;

public class ActionAttack extends Action {
    
    public final Player target;

    public ActionAttack(Player player, Player target){
        super(player, true);
        this.target = target;
    }

    public void Effect() {
    }

    public static HashMap<ResourceType, Integer>  Cost(){
        return null;
    }

    public static boolean isPossible(Player player, Game game){
        //TODO: Le player doit avoir au moins un camp? sur la même ile que l'autre joueur? 
        for(Map.Entry<Building, Player> entry : game.board.getBuildings().entrySet())    {
            if(entry.getValue() == player && (entry.getKey() instanceof Army  || entry.getKey() instanceof Camp)){
                Position pos = entry.getKey().tile.position;
                game.board.GetTilesNeighborhood(pos.x, pos.y); // exemple avec voisins directs
                
                int ile = entry.getKey().islandId; //exemple avec island id

                //To be continued...
            } 
        }
        return false;
    }

    public boolean CheckInstancePossible(Player player, Game game){
        //TODO: demander une tile spécifique à attaquer, vérifier qu'elle contient un batiment, etc...
        return true;
    }

    public String Description(){
        return source.toString() + " attaque " + target.toString() + "!";
    }
}
