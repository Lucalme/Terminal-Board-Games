package action.actions;

import action.Action;
import Game.Game;
import player.Player;

public class ActionSkip extends Action {

    
    public ActionSkip(Player player){
        super(player, true);
    }

    public void Effect() {
    }

    public static boolean isPossible(Player player, Game game){
        return true;
    }

    @Override
    public String Description() {
        return source.toString() +" passe son tour...";
    }
    
    public boolean CheckInstancePossible(Player player, Game game){
        return true;
    }

}
