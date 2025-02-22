package action.actions;
import java.util.HashMap;

import Game.Game;
import action.Action;
import board.resource.ResourceType;
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
