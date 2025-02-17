package action.actions;

import Game.Game;
import action.Action;
import action.util.IO;
import board.resource.ResourceType;
import player.Player;

public class ShowInventory extends Action {

    public ShowInventory(Player source){
        super(source, false);
    }

    @Override
    public void Effect() {
        source.showResources();
        IO.Next();
        IO.DeleteLines(ResourceType.values().length +1);
    }

    public static boolean isPossible(Player player, Game game) {
        return true;
    }

    @Override
    public String Description() {   
        return null;
    }
    
    public boolean CheckInstancePossible(Player player, Game game){
        return true;
    }
}
