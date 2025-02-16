package action;

import java.util.HashMap;

import action.util.IO;
import board.resource.ResourceType;
import player.Player;

public class ShowInventory extends Action {

    public ShowInventory(Player source){
        super(source, false);
    }

    @Override
    public boolean Effect() {
        source.showResources();
        IO.Next();
        IO.DeleteLines(ResourceType.values().length +1);
        return true;
    }

    @Override
    public HashMap<ResourceType, Integer> Cost() {
        return null;
    }

    @Override
    public String Description() {   
        return null;
    }
    
}
